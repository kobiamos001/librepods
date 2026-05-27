package me.kavishdevar.librepods

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import io.github.libxposed.service.XposedService
import io.github.libxposed.service.XposedServiceHelper
import me.kavishdevar.librepods.billing.BillingManager
import me.kavishdevar.librepods.billing.BillingProviderFactory
import me.kavishdevar.librepods.utils.XposedServiceHolder
import me.kavishdevar.librepods.utils.XposedState

class LibrePodsApplication: Application(), XposedServiceHelper.OnServiceListener, DefaultLifecycleObserver {

    override fun onCreate() {
        XposedServiceHelper.registerListener(this)
        BillingManager.provider = BillingProviderFactory.create(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        super<Application>.onCreate()
        
        // כפיית מצב שבו האפליקציה חושבת ש-Xposed פעיל ויש הרשאות בלוטוס
        XposedState.isAvailable = true
        XposedState.bluetoothScopeEnabled = true
    }

    override fun onResume(owner: LifecycleOwner) {
        BillingManager.provider.queryPurchases()
        
        // עוקף את הבדיקות המקוריות - הכל פתוח וזמין
        XposedState.isAvailable = true
        XposedState.bluetoothScopeEnabled = true
    }

    override fun onServiceBind(service: XposedService) {
        XposedServiceHolder.service = service
        
        // תמיד True
        XposedState.isAvailable = true
        XposedState.bluetoothScopeEnabled = true
    }

    override fun onServiceDied(p0: XposedService) {
        XposedServiceHolder.service = null
        
        // גם אם השירות מתנתק, האפליקציה תמשיך לאפשר את התכונות
        XposedState.isAvailable = true
        XposedState.bluetoothScopeEnabled = true
    }
}
