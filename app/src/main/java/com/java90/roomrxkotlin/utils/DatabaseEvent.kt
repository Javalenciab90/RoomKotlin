package com.java90.roomrxkotlin.utils

/** mObserverSubject is a RxJava PublishSubject responsible
 for real-time observing of database events
 */
data class DatabaseEvent <T>(val eventType: DataEventType, val value: T)
