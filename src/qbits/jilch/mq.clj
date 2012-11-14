(ns qbits.jilch.mq
  (:refer-clojure :exclude [send])
  (:import [org.jeromq ZMQ ZMQ$Context ZMQ$Socket]))

(defprotocol PByteArrayEncoder
  (encode [value] "Encodes input to byte array for use as topic or messages values"))

(extend-protocol PByteArrayEncoder
  String
  (encode [value]
    (.getBytes value))

  Object
  (encode [message]
    message)

  nil
  (encode [_]
    (byte-array [])))

(defn context [threads]
  (ZMQ/context threads))

(defmacro with-context
  [id threads & body]
  `(let [~(with-meta id {:tag "org.jeromq.ZMQ$Context"}) (context ~threads)]
     (try ~@body
          (finally (.term ~id)))))

(def sndmore ZMQ/SNDMORE)
(def noblock 0)
(def req ZMQ/REQ)
(def rep ZMQ/REP)
;; (def xreq ZMQ/XREQ)
;; (def xrep ZMQ/XREP)
(def pub ZMQ/PUB)
(def sub ZMQ/SUB)
(def pair ZMQ/PAIR)
(def push ZMQ/PUSH)
(def pull ZMQ/PULL)

(defn socket
  [^ZMQ$Context context type]
  (.socket context type))

(defn bind
  [^ZMQ$Socket socket url]
  (doto socket
    (.bind url)))

(defn connect
  [^ZMQ$Socket socket url]
  (doto socket
    (.connect url)))

(defn subscribe
  [^ZMQ$Socket socket & [topic]]
  (doto socket
    (.subscribe (encode topic))))

(defn unsubscribe
  [^ZMQ$Socket socket & [topic]]
  (doto socket
    (.unsubscribe (encode topic))))

(defn send
  ([^ZMQ$Socket socket message flags]
     (.send socket (encode message) flags))
  ([^ZMQ$Socket socket message]
     (send socket message noblock)))

(defn recv
  ([^ZMQ$Socket socket flags]
     (.recv socket flags))
  ([^ZMQ$Socket socket]
     (recv socket 0)))

(defn recv-all
  ([^ZMQ$Socket socket flags]
     (loop [acc []]
       (let [msg (recv socket flags)]
         (if (.hasReceiveMore socket)
           (recur (conj acc msg))
           (conj acc msg)))))
  ([^ZMQ$Socket socket]
     (recv-all socket 0)))

(defn identify
  [^ZMQ$Socket socket name]
  (.setIdentity socket (encode name)))

(defn set-linger
  [^ZMQ$Socket socket linger-ms]
  (doto socket
    (.setLinger (long linger-ms))))

(defn set-hwm
  [^ZMQ$Socket socket hwm]
  (if hwm
    (doto socket
      (.setHWM (long hwm)))
    socket))
