(defproject boiler-pipe "0.1.0-SNAPSHOT"
  :description "boiler-pipe: boiler-pipe is a wrapper Clojure library on the boilerpipe
  library for retrieving the content from the html representation of the webpages\n
  "
  :url "http://github.com/bugra/boiler-pipe"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [de.l3s.boilerpipe/boilerpipe "1.1.0"]
                 [xerces/xercesImpl "2.11.0"]; boiler-pipe dependency
                 [net.sourceforge.nekohtml/nekohtml "1.9.21"]; boiler-pipe dependency
                 ]
  :main ^:skip-aot boiler-pipe.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
