package com.example.thomas.letsgo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class SectionPagerAdapter extends FragmentPagerAdapter{
    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                RequestFragment requestFragment=new RequestFragment();
                return requestFragment;

            case 1:
                ChatsFragment chatsFragment=new ChatsFragment();
                return chatsFragment;

            case 2:
                EventsFragment eventsFragment =new EventsFragment();
                return eventsFragment;

            case 3:
                FriendsFragment friendsFragment =new FriendsFragment();
                return friendsFragment;

                default:
                    return null;
        }



    }

    @Override
    public int getCount() {
        return 4;
    }
    public  CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return"SEND REQUEST";
            case 1:
                return " CHAT";

            case 2:
                return "CREATE EVENTS";

                case 3:
                    return "FRIENDS";
                    default:
                        return  null;
        }

    }
}
