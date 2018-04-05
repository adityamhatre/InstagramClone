package com.thelegacycoder.instagramclone

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_login.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = FirebaseFirestore.getInstance()

        username.setText("adityamhatre")
        password.setText("password")

        login_button.setOnClickListener {
            val username = username.text.toString()
            val password = password.text.toString()
            (activity as HomeActivity).mAuth.signInWithEmailAndPassword("$username@instagramclone.com", password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    db.collection("users")
                            .whereEqualTo("username", username)
                            .whereEqualTo("password", password)
                            .limit(1)
                            .get()
                            .addOnCompleteListener {
                                val user = it.result.documents[0].toObject(User::class.java)
                                if (it.isSuccessful) {
                                    (activity as HomeActivity).gotoActivity(WallActivity::class.java, user)
                                } else {
                                    println("Not successful")
                                    println(task.result)
                                    Toast.makeText(activity, "Username and/ or password is incorrect", Toast.LENGTH_SHORT).show()
                                }
                            }
                }
            }

        }
        login_button.performClick()


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
        fun newInstance(): LoginFragment = LoginFragment()
    }
}
