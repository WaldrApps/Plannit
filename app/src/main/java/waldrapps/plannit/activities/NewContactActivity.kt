package waldrapps.plannit.activities

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_new_contact.*
import waldrapps.plannit.R
import waldrapps.plannit.fragments.NameDialogFragment
import waldrapps.plannit.fragments.NewContactFragment

class NewContactActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        setSupportActionBar(toolbar)
        //Set back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        //Get name initially
        NameDialogFragment.newInstance(null).show(supportFragmentManager, "NameDialogFragment")
        //Allow user to edit name by clicking on title
        toolbar.setOnClickListener { NameDialogFragment.newInstance(toolbar.title.toString()).show(supportFragmentManager, "NameDialogFragment") }

        //Start new contact fragment
        if (savedInstanceState == null) {
            //Pass uuid to fragment
            val bundle = Bundle()
            bundle.putString(getString(R.string.uuid), intent.getStringExtra(getString(R.string.uuid)))
            //Start fragment
            val fragment = NewContactFragment.newInstance()
            fragment.arguments = bundle
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()
        }
    }
}