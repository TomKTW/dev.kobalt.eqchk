package dev.kobalt.eqchk.android.main

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.SimpleStateChanger
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentStateChanger
import com.zhuinden.simplestackextensions.navigatorktx.backstack
import dev.kobalt.eqchk.android.R
import dev.kobalt.eqchk.android.base.BaseActivity
import dev.kobalt.eqchk.android.databinding.MainBinding
import dev.kobalt.eqchk.android.details.DetailsKey
import dev.kobalt.eqchk.android.home.HomeKey

class MainActivity : BaseActivity<MainBinding>(), SimpleStateChanger.NavigationHandler {

    companion object {
        const val lastIdKey = "lastId"
    }

    private lateinit var fragmentStateChanger: DefaultFragmentStateChanger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentStateChanger = DefaultFragmentStateChanger(supportFragmentManager, R.id.container)
        Navigator.configure()
            .setStateChanger(SimpleStateChanger(this))
            .install(this, viewBinding.container, History.single(HomeKey()))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 0
            )
        }
        onIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        onIntent(intent)
    }

    private fun onIntent(intent: Intent?) {
        intent?.getStringExtra(lastIdKey)?.let {
            backstack.goTo(DetailsKey(it))
        }
    }

    override fun onBackPressed() {
        if (!backstack.goBack()) super.onBackPressed()
    }

    override fun onNavigationEvent(stateChange: StateChange) {
        fragmentStateChanger.handleStateChange(stateChange)
    }

}
