package com.example.mymenuclientapp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mymenuclientapp.api.ServiceBuilder
import com.example.mymenuclientapp.api.UserService
import com.example.mymenuclientapp.models.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 * Use the [AddUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class AddUserFragment : Fragment() {

    private lateinit var email: EditText
    private lateinit var lastName: EditText
    private lateinit var firstName: EditText
    private lateinit var numberOfPeople: EditText
    private lateinit var phoneNumber: EditText
    var mSendDataInterface: SendDataInterface? = null

    public interface SendDataInterface {
        fun sendData(data: UserModel?)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_add_user, container, false)

        email = view!!.findViewById(R.id.email_text_view)
        lastName = view!!.findViewById(R.id.last_name_text_view)
        firstName = view!!.findViewById(R.id.first_name_text_view)
        numberOfPeople = view!!.findViewById(R.id.number_of_persons_text_view)
        phoneNumber = view!!.findViewById(R.id.phone_text_view)

        val b = view!!.findViewById<View>(R.id.buttonExit) as Button
        b.setOnClickListener {
            Log.d("BUTTON", "exit BUTTON PRESSED")
            parentFragmentManager.beginTransaction().remove(this@AddUserFragment).commit()
        }

        val createUserButton = view!!.findViewById<Button>(R.id.create_user_button)
        createUserButton.setOnClickListener {
            if (checkFormValidity())
            {
                val user = UserModel(email.text.toString(),
                    lastName.text.toString(),
                    firstName.text.toString(),
                    phoneNumber.text.toString(),
                    numberOfPeople.text.toString().toInt())
                createUser(user)
            }
        }

        return view
    }

    private fun checkFormValidity() = (email.text.isNotEmpty() && lastName.text.isNotEmpty()
            && firstName.text.isNotEmpty() && phoneNumber.text.isNotEmpty()
            && numberOfPeople.text.isNotEmpty())

    private fun createUser(user: UserModel) {
        val destinationService = ServiceBuilder.buildService(UserService::class.java)
        val requestCall = destinationService.createUser(user)

        requestCall.enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (response.isSuccessful) {
                    val user = response.body()!!
                    mSendDataInterface?.sendData(user)
                    parentFragmentManager.beginTransaction().remove(this@AddUserFragment).commit()
                } else {
                    Toast.makeText(
                        context,
                        "Something went wrong ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Toast.makeText(context, "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activity = context as Activity
        try {
            mSendDataInterface = activity as SendDataInterface
        } catch (e: RuntimeException) {
            throw RuntimeException("$activity must implement method")
        }
    }
}