package waldrapps.plannit.activities

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_group.*
import waldrapps.plannit.R
import waldrapps.plannit.fragments.GroupFragment
import waldrapps.plannit.fragments.NameDialogFragment

class GroupActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        //Set toolbar
        setSupportActionBar(toolbar)
        //Set back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        //Get name initially
        NameDialogFragment.newInstance(null).show(supportFragmentManager, "NameDialogFragment")
        //Allow user to edit name by clicking on title
        toolbar.setOnClickListener { NameDialogFragment.newInstance(toolbar.title.toString()).show(supportFragmentManager, "NameDialogFragment") }

        //Start group fragment
        if (savedInstanceState == null) {
            //Pass uuid to fragment
            val bundle = Bundle()
            bundle.putString(getString(R.string.uuid), intent.getStringExtra(getString(R.string.uuid)))
            //Start fragment
            val fragment = GroupFragment.newInstance()
            fragment.arguments = bundle
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()
        }
    }
}