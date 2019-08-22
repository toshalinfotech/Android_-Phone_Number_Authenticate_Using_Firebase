# Android Phone Number Authenticate Using Firebase
This Example demonstrates how you can use Firebase Authentication to sign in a user by sending an SMS
message to the user's phone. The user signs in using a one-time code contained in the SMS message.

# Requirements:
1. Android Studio 3.4
2. If you haven't already, <a href="https://firebase.google.com/docs/android/setup">add Firebase to your Android project</a>

# 1.Firebase Register Project Steps:
<b>Step: 1.1</b></br>
![step_one](app/src/main/res/drawable/step_one)

<b>Step: 1.2</b></br>
![step_two](app/src/main/res/drawable/step_two)

<b>Step: 1.3:</b></br>
![step_three](app/src/main/res/drawable/step_three)

<b>Step: 1.4:</b></br>
![step_four](app/src/main/res/drawable/step_four)

# 2.Once the Firebase setup is complete, go to the Firebase console and follow these steps:

<b>Step: 2.1:</b> Click on <b>Develop</b> from the left navigation bar and then click <b>Authentication</b>.</br>
![step_five](app/src/main/res/drawable/step_five)

<b>Step: 2.2:</b> Now, navigate to <b>SIGN-IN METHOD</b> tab:</br>
![step_eight](app/src/main/res/drawable/step_eight)</br>
![step_six](app/src/main/res/drawable/step_six)

<b>Step: 2.3:</b> Enable the Phone Authentication.</br>
![step_seven](app/src/main/res/drawable/step_seven)

# Security concerns:
Authentication using only a phone number, while convenient, is less secure than the other available methods, because possession of a phone number can be easily transferred between users. Also, on devices with multiple user profiles, any user that can receive SMS messages can sign in to an account using the device's phone number.
# Note:
This example is only work on actual devices and will not work under simulator.


# Output:
![out_put_one](app/src/main/res/drawable/out_put_one) ![out_put_two](app/src/main/res/drawable/out_put_two)
