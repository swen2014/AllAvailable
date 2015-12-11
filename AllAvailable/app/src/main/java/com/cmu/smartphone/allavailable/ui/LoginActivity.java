package com.cmu.smartphone.allavailable.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cmu.smartphone.allavailable.R;
import com.cmu.smartphone.allavailable.entities.ReservationView;
import com.cmu.smartphone.allavailable.model.ScheduleListItem;
import com.cmu.smartphone.allavailable.util.DateTimeHelper;
import com.cmu.smartphone.allavailable.util.JsonHelper;
import com.cmu.smartphone.allavailable.ws.remote.DataReceiver;
import com.cmu.smartphone.allavailable.ws.remote.ServerConnectionTask;
import com.cmu.smartphone.allavailable.ws.remote.SessionControl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * The Login Page that offers login via email/password.
 *
 * @author Xi Wang
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private View mLogoView;

    /**
     * The override onCreate method
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mLogoView = findViewById(R.id.login_logo);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Login to the server
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
//            String uriHost = getResources().getText(R.string.host).toString();
            SessionControl session = SessionControl.getInstance();
            String uriHost = session.getHostIp(this);
            mAuthTask.execute(uriHost);
        }
    }

    /**
     * Check the email
     *
     * @param email
     * @return
     */
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    /**
     * Check the password
     *
     * @param password
     * @return
     */
    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mLogoView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLogoView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLogoView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Override onCreateLoader method
     *
     * @param i
     * @param bundle
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    /**
     * Override onLoadFinished method
     *
     * @param cursorLoader
     * @param cursor
     */
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    /**
     * Override the onLoaderReset method
     *
     * @param cursorLoader
     */
    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    /**
     * The auto generated profile Query method
     */
    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    /**
     * The task to login the server
     */
    public class UserLoginTask extends AsyncTask<String, Void, Boolean>
            implements ServerConnectionTask {

        private final String mEmail;
        private final String mPassword;
        private ArrayList<ReservationView> dataResults;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        /*
        * (non-Javadoc)
        *
        * @see android.os.AsyncTask#doInBackground
        */
        @Override
        protected Boolean doInBackground(String... params) {

            String response = null;
            URL url = null;
            boolean result = false;
            try {
                url = new URL(params[0] + "LogIn?action=login&uid=" + mEmail + "&pwd=" + mPassword);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                int code = connection.getResponseCode();
                if (code == 200) {
                    response = DataReceiver.ChangeInputStream(connection
                            .getInputStream()).replace("\n", "");
                    if (response != null && response.equals(mEmail)) {
                        result = true;
                    }
                }

                if (result) {
                    Calendar cal = Calendar.getInstance();
                    Date time = cal.getTime();

                    CharSequence dateChar = DateFormat.format("MMM dd, yyyy", time);
                    CharSequence timeChar = DateFormat.format("HH:mm", time);
                    StringBuilder sb = new StringBuilder();
                    sb.append(params[0]);
                    sb.append("Reservation?action=reservations&user=");
                    sb.append(mEmail);
                    sb.append("&date=");
                    sb.append(dateChar.toString());
                    sb.append("&time=");
                    sb.append(timeChar.toString());
                    sb.append("&history=false");

                    url = new URL(sb.toString().replace(" ", "%20"));
                    Log.v("DEBUG", url.toString());
                    connection = (HttpURLConnection) url
                            .openConnection();
                    connection.setConnectTimeout(3000);
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    code = connection.getResponseCode();
                    if (code == 200) {
                        String jsonString = DataReceiver.ChangeInputStream(connection
                                .getInputStream());
                        dataResults = (ArrayList<ReservationView>) JsonHelper.getReservations(jsonString);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        /*
        * (non-Javadoc)
        *
        * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
        */
        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
                ArrayList<ScheduleListItem> list = new ArrayList<>();
                for (ReservationView reservation : dataResults) {
                    String dateString = reservation.getReservation().getDate();
                    String timeString = reservation.getReservation().getTime();
                    double duration = reservation.getReservation().getDuration();
                    try {
                        Date date = formatter.parse(dateString);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);

                        ScheduleListItem item = new ScheduleListItem();
                        StringBuilder sb = new StringBuilder();
                        sb.append(DateTimeHelper.getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK) - 1));
                        sb.append(", ");
                        sb.append(timeString);
                        sb.append(" - ");
                        sb.append(DateTimeHelper.addTime(timeString, duration));
                        item.setTime(sb.toString());

                        String building = reservation.getBuilding().getBuildingName().
                                substring(reservation.getBuilding().getBuildingName().indexOf(",") + 2);
                        item.setPlace(building + ", " + reservation.getRoom().getName());
                        list.add(item);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                SessionControl session = SessionControl.getInstance();
                session.createUserSession(LoginActivity.this, mEmail);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("reservations", dataResults);
                intent.putExtra("schedules", list);
                startActivity(intent);
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

