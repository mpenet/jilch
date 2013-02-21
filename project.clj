(defproject cc.qbits/jilch "0.3.0"
  :description "Jilch: A Clojure 0MQ Messaging Library (using jeromq)"
  :url "https://github.com/mpenet/jilch"
  :repositories {"sonatype-snapshots" "https://oss.sonatype.org/content/repositories/snapshots"
                 "sonatype" "https://oss.sonatype.org/content/repositories/releases/"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.jeromq/jeromq "0.2.0"]]
  :profiles {:1.4  {:dependencies [[org.clojure/clojure "1.4.0"]]}
             :1.5  {:dependencies [[org.clojure/clojure "1.5.0-master-SNAPSHOT"]]}
             :test {:dependencies []}})
