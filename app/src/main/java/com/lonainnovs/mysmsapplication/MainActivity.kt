package com.lonainnovs.mysmsapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import android.telephony.SmsManager
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.lonainnovs.mysmsapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //declaring the variables
//    lateinit var button: Button
//    lateinit var recipient_number: EditText
//    lateinit var your_message: EditText
   lateinit var binding: ActivityMainBinding
    private val permissionRequest = 901

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun sendMessage()
    {
        //fetching the number from edit text field
        val number: String = binding.recipientPhoneNumber.text.toString().trim()
        //fetching your message from edit text
        val msg: String = binding.yourMessage.text.toString().trim()
        if (number == "" || msg == "")
        {
            //if number or message is empty
            Toast.makeText(this, "Please enter the  missing fields", Toast.LENGTH_SHORT).show()
        }
        else
        {
            //if both number and message is not empty
            if (TextUtils.isDigitsOnly(number))
            {
                //if number contains only digits
                //creating sms manager object
                val smsManager: SmsManager = SmsManager.getDefault()
                //use sms manager api to send the message
                smsManager.sendTextMessage(number, null, msg, null, null)
                //if the message is sent successfully then show toast
                Toast.makeText(this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show()
            }
            else
            {
                //if number has other than digits then show wrong number
                Toast.makeText(this, "Wrong Number!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults:
    IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //checking if the permissions are granted
        if (requestCode == permissionRequest) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendMessage()
            } else {
                Toast.makeText(this, "Please provide permission",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun sendSMS(view: View)
    {
        //when you press the button
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED)
        {
            //if permission is granted, then send the message
            sendMessage()
        }
        else
        {
            //if not granted, then seek again
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS),
                permissionRequest)
        }
    }
}