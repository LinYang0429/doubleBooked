(use '[clojure.core.logic])
(require '[clj-time.core :as t])

(deftype Event [eventName startTime endTime])
(defn eventPair [event1 event2] (->Pair event1 event2))

(defn doublebooked? [eventPair]
    (or (and (t/before? (:startTime (:event1 eventPair)) (:endTime (:event2 eventPair)))
         (t/before? (:startTime (:event2 eventPair)) (:endTime (:event1 eventPair))))
        (and (t/before? (:startTime (:event2 eventPair)) (:endTime (:event1 eventPair)))
         (t/before? (:startTime (:event1 eventPair)) (:endTime (:event2 eventPair))))))    

(defn makePair [event1 event2]
    (eventPair[event1 event2]))

(defn makePairs [event1 otherEvents]
    (mapcat (makePair [event1 (first otherEvents)]) otherEvents))

(defn findEventsPairs [events] 
    (mapcat (makePairs [(first events) (rest events)]) events))

(defn doublebooked [events]
    (filter doublebooked? (findEventsPairs [events])))