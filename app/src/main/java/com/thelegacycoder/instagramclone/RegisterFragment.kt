package com.thelegacycoder.instagramclone

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_register.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    private var mListener: OnFragmentInteractionListener? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = FirebaseFirestore.getInstance()

        username.setText("adityamhatre")
        password.setText("password")
        confirm_password.setText("password")
        age.setText("23")
        location.setText("Arlington, TX")
        full_name.setText("Aditya Mhatre")




        register_button.setOnClickListener {
            val username = username.text.toString()
            val password = password.text.toString()
            val age = age.text.toString().toInt()
            val location = location.text.toString()
            val fullName = full_name.text.toString()
            val confirmPassword = confirm_password.text.toString()

            if (password == confirmPassword) {
                val user = User(username, password, age, location, fullName)

                (activity as HomeActivity).mAuth.createUserWithEmailAndPassword("${user.username}@instagramclone.com", user.password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        println("successful")
                        db.collection("users")
                                .document()
                                .set(user)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        println("db success")
                                        Toast.makeText(activity, "Registered", Toast.LENGTH_SHORT).show()
                                        (activity as HomeActivity).gotoActivity(WallActivity::class.java, user)
                                    } else {
                                        println("db not success")
                                        System.err.print(it.result)
                                    }
                                }
                    } else {
                        println("not successful")
                        System.err.print(it.result)
                        Toast.makeText(activity, "Username already exists", Toast.LENGTH_SHORT).show()
                    }

                }


            } else {
                Toast.makeText(activity, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }

        }

    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    companion object {
        fun newInstance(): RegisterFragment = RegisterFragment()
    }
}// Required empty public constructor
