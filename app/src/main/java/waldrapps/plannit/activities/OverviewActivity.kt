package waldrapps.plannit.activities

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_overview.*
import waldrapps.plannit.R
import waldrapps.plannit.fragments.OverviewFragment
import waldrapps.plannit.utils.Constants

class OverviewActivity : BaseActivity() {

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        //Set toolbar
        setSupportActionBar(toolbar)
        //Set back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        //Start overview fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(
                    R.id.fragment_container,
                    OverviewFragment.newInstance(intent.getStringExtra(Constants.ARG_CONTACT_ID))
            ).commit()
        }
    }
}