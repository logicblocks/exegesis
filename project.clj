(defproject exegesis "0.1.0-SNAPSHOT"
  :description "Simplify reflection of annotations on Java types."
  :url "http://github.com/tobyclemson/exegesis"
  :license {:name "The MIT License"
            :url  "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :source-paths ["src/clojure"]
  :test-paths ["test/clojure"]
  :java-source-paths ["src/java" "test/java"]
  :deploy-repositories [["releases" {:url     :clojars
                                     :creds   :gpg
                                     :signing {:gpg-key "44CE9D82"}}]])
