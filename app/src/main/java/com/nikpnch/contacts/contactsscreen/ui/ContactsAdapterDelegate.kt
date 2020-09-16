package com.nikpnch.contacts.contactsscreen.ui

import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.nikpnch.contacts.Item
import com.nikpnch.contacts.R
import com.nikpnch.contacts.contactsscreen.ui.model.ContactsModel
import kotlinx.android.synthetic.main.item_contact.*

fun contactsAdapterDelegate(onClick: (Int) -> Unit): AdapterDelegate<List<Item>> =
    adapterDelegateLayoutContainer<ContactsModel, Item>(
        R.layout.item_contact
    ) {
        bind {
            clItemContact.setOnClickListener {
                onClick(adapterPosition)
            }
            tvName.text = item.name
            tvPhoneNumber.text = item.phoneNumber
            Glide.with(containerView)
                .load(item.avatar)
                .circleCrop()
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(ivAvatar)
        }
    }