package com.example.mymenuclientapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.MealsModel
import com.example.mymenuclientapp.models.UserModel

/**
 * A simple [Fragment] subclass.
 * Use the [AddFoodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFoodFragment : Fragment() {
    private lateinit var foodName: EditText

    var mSendDataInterface: SendFoodDataInterface? = null

    public interface SendFoodDataInterface {
        fun sendData(data: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_add_food, container, false)

        foodName = view!!.findViewById(R.id.meal_name_text_view)

        val b = view!!.findViewById<View>(R.id.buttonFoodExit) as Button
        b.setOnClickListener {
            Log.d("BUTTON", "exit BUTTON PRESSED")
            parentFragmentManager.beginTransaction().remove(this@AddFoodFragment).commit()
        }

        val createMealButton = view!!.findViewById<Button>(R.id.create_meal_button)
        createMealButton.setOnClickListener {
            if (foodName.text.isNotEmpty())
            {
                create(foodName.text.toString())
            }
        }
        return view
    }

    private fun create(name: String) {
        val item: MealsModel = MealsModel.builder()
            .name(name)
            .build()
        Amplify.DataStore.save(
            item,
            { success -> Log.i("Amplify", "Saved item: " + success.item().name) },
            { error -> Log.e("Amplify", "Could not save item to DataStore", error) }
        )
    }
}