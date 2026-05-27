/*
    LibrePods - AirPods liberated from Apple’s ecosystem
    Copyright (C) 2025 LibrePods contributors
    ...
*/

package me.kavishdevar.librepods.billing

import android.app.Activity
import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow

object BillingProviderFactory {

    fun create(context: Context): BillingProvider {
        // מחזיר ספק מותאם אישית שפותח את כל תכונות הפרימיום לצמיתות
        return object : BillingProvider {
            override val isPremium = MutableStateFlow(true) // פרימיום מופעל תמיד
            override val price = MutableStateFlow("Unlocked")

            override fun purchase(activity: Activity) { 
                // אין צורך לעשות כלום, כבר פתוח
            }
            
            override fun queryPurchases() { 
                isPremium.value = true
            }
            
            override fun restorePurchases() { 
                isPremium.value = true
            }
        }
    }
}
