(ns jilch.test.mq
  (:use clojure.test)
  (:import java.util.UUID)
  (:require [jilch.mq :as mq]))

(defn uuid [] (str (UUID/randomUUID)))

(def url
     (str "inproc://" (uuid))
     ;; (str "ipc://" (uuid))
     ;; (str "tcp://127.0.0.1:" (+ 4000 (Math/round (rand 1000)))))
     )

(deftest jilch
  (testing "jilch"
    (testing "should be able to"

      (testing "push / pull"
        (mq/with-context context 2
          (with-open [s0 (-> context
                             (mq/socket mq/pull)
                             (mq/bind url))
                      s1 (-> context
                             (mq/socket mq/push)
                             (mq/connect url))]
            (let [msg (uuid)
                  push (future (mq/send s1 msg))
                  pull (future (mq/recv s0))]
              (is (= msg (String. @pull)))))))

      (testing "pub / sub"
        (mq/with-context context 2
          (with-open [s0 (-> context
                             (mq/socket mq/pub)
                             (mq/bind url))
                      s1 (-> context
                             (mq/socket mq/sub)
                             (mq/subscribe)
                             (mq/connect url))]
            (let [msg (uuid)
                  pub (future (mq/send s0 msg))
                  sub (future (mq/recv s1))]
              (is (= msg (String. @sub)))))))

      (testing "pair / pair"
        (mq/with-context context 2
          (with-open [s0 (-> context
                             (mq/socket mq/pair)
                             (mq/bind url))
                      s1 (-> context
                             (mq/socket mq/pair)
                             (mq/connect url))]
            (let [msg0 (uuid)
                  pair0 (future (mq/send s0 msg0)
                                (mq/recv s0))
                  msg1 (uuid)
                  pair1 (future (mq/send s1 msg1)
                                (mq/recv s1))]
              (is (= msg1 (String. @pair0)))
              (is (= msg0 (String. @pair1)))))))

      (testing "req / rep"
        (mq/with-context context 2
          (with-open [s0 (-> context
                             (mq/socket mq/rep)
                             (mq/bind url))
                      s1 (-> context
                             (mq/socket mq/req)
                             (mq/connect url))]
            (let [msg (uuid)
                  req (future (mq/send s1 msg)
                              (mq/recv s1))
                  rep (future (mq/recv s0)
                              (mq/send s0 msg))]
              (is (= msg (String. @req)))))))

      (testing "req / xrep")

      (testing "xreq / rep")

      (testing "xreq / xrep"))))
