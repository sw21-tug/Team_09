# Team_09
## Modern Newsgroup App
The motivation for this project is that students of the TU Graz need the TU Graz newsgroup for a lot of their lectures. Actually, there is no really good client for Android available. Most of the available apps are not really free and filled with ads. Furthermore, the existing solutions are only simple clients and do not offer more than that.

The idea is to create an app, which will not only be a simple newsgroup reader. Additionally, it will have features like setting aliases for all your groups and subgroups, filter and sort entries, set message threads to your personal favourites to follow them and see your own messages and threads in a different tab/view.
Furthermore, it will have an offline user profile for storing all of these settings, managing further personal settings and with the additional functionality to transfer all of these settings to a new phone.

This solution is specially designed for the TU Graz, but can be used for all other kind of newsgroups as well.

## Members & Roles:
Member| Role
-------- | -------- 
Christina Tögl   | Product Owner
Jakob Elias Dunst | Scrum Master
Irfan Jahić | Developer
Tamara Steinwender | Developer
Patrick Struger | Developer
Alexander Toch | Developer
Bernadette Vogl | Developer
Michael Weiß | Developer

## Currently implemented Features
User Story | Feature
-------- | -------- 
[MNA-001](https://github.com/sw21-tug/Team_09/issues/1)  | Subscription
[MNA-002](https://github.com/sw21-tug/Team_09/issues/2)  | Cancel Subscription
[MNA-003](https://github.com/sw21-tug/Team_09/issues/3)  | Subgroups
[MNA-004](https://github.com/sw21-tug/Team_09/issues/4)  | Cancel Subgroups
[MNA-005](https://github.com/sw21-tug/Team_09/issues/5)  | Message Threads
[MNA-006](https://github.com/sw21-tug/Team_09/issues/6)  | Post Messages
[MNA-007](https://github.com/sw21-tug/Team_09/issues/7)  | Set Server names
[MNA-008](https://github.com/sw21-tug/Team_09/issues/8)  | Edit Server names
[MNA-009](https://github.com/sw21-tug/Team_09/issues/9)  | Set Aliases
[MNA-010](https://github.com/sw21-tug/Team_09/issues/10)  | Delete Aliases
[MNA-011](https://github.com/sw21-tug/Team_09/issues/11)  | Edit Aliases
[MNA-014](https://github.com/sw21-tug/Team_09/issues/14)  | Group Overview
[MNA-015](https://github.com/sw21-tug/Team_09/issues/19)  | Start / Splash Screen
[MNA-016](https://github.com/sw21-tug/Team_09/issues/20)  | Feedback Messages
[MNA-017](https://github.com/sw21-tug/Team_09/issues/37)  | Change Language
[MNA-018](https://github.com/sw21-tug/Team_09/issues/47)  | Remember subscribed newsgroups and settings
[MNA-019](https://github.com/sw21-tug/Team_09/issues/28)  | Replace Logo
[MNA-022](https://github.com/sw21-tug/Team_09/issues/53)  | Input Validation
[MNA-024](https://github.com/sw21-tug/Team_09/issues/60)  | Multiple Newsgroup Server Subscription
[MNA-025](https://github.com/sw21-tug/Team_09/issues/59)  | Fix Failing Test Cases
[MNA-026](https://github.com/sw21-tug/Team_09/issues/63)  | Update DB
[MNA-027](https://github.com/sw21-tug/Team_09/issues/70)  | App should not crash
[MNA-BUG-001](https://github.com/sw21-tug/Team_09/issues/55)  | Dark Mode View


### Description of these Features:
There is always a splash screen when you launch the app, which displays the name and the logo of the app. This screen disappears after some seconds. 
After the splash screen disappears, the subscribe screen appears. You can subscribe to a newsgroup server now, which subsequently shows a tree including all subgroups of this server and a filter to search for a specific one. When you check your preferred newsgroup and click on "FINISH" these subgroups where subscribed and you see the next screen with the subscribed groups listed.

When you click then into one of the subscribed newsgroups in the list, you can see the message threads of the group. These message threads can be expanded with their expander to see which messages are inside of it. The content of a message you simply can be read by tapping on the message thread (or expand it and tap on a sub message of it). A thread can be answered by tapping the arrow icon. To start a new thread, you have to navigate into the newsgroup and tap the plus icon. There you can type the subject and the message and tap at the send icon.

A subscribed newsgroup can be deleted by either unchecking it in the tree of subscribed newsgroup (which can be called with the plus at the bottom of the newsgroup list) or by swiping the newsgroup to the left. A trash bin appears and the newsgroup will be deleted.

Additionally, it is also possible to swipe to the right to enter an "Alias" for the newsgroup. You can set, edit or delete the local alias for the newsgroup.
With the menu bar at the top of the app it is possible to navigate between your subscribed newsgroup servers. With the plus next to the menu it is possible to add a new newsgroup server to your list of subscribed servers (and to the menu).

Furthermore, it is always possible to cancel a subscription to a newsgroup server in the settings (click on "DELETE") or to change the name ("Alias") of it. It is also possible to change the language of the application in the user profile. The language can be changed to Russian or English. 

The app is also provided with toast messages, which were used for further implementations. They were used as a feedback system to notify in case something went wrong and also when it was successful. Additionally, they were used for input validation of all input fields.

