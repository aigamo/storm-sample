(defproject storm-sample "0.0.1-SNAPSHOT"
  :source-path "src/clj"
  :java-source-path "src/main/java"
  :javac-options {:debug "true" :fork "true"}
  :resources-path "multilang"
  :aot :all
  :repositories {
                 "twitter4j" "http://twitter4j.org/maven2"
                 }

  :dependencies [
                 [org.twitter4j/twitter4j-core "2.2.6-SNAPSHOT"]
                 [org.twitter4j/twitter4j-stream "2.2.6-SNAPSHOT"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.codehaus.jackson/jackson-jaxrs "1.9.9"]
                 ]

  :dev-dependencies [[storm "0.8.1"]
                     [org.clojure/clojure "1.4.0"]
                     [org.codehaus.jackson/jackson-jaxrs "1.9.9"]
                     ])

