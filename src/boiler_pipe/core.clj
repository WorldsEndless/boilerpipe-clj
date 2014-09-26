(ns boiler-pipe.core
  (:gen-class)
  (:import (de.l3s.boilerpipe.extractors ArticleExtractor)
           (de.l3s.boilerpipe.extractors DefaultExtractor)
           (de.l3s.boilerpipe.extractors LargestContentExtractor)
           (de.l3s.boilerpipe.extractors CanolaExtractor)
           (de.l3s.boilerpipe.extractors KeepEverythingExtractor)
           (de.l3s.boilerpipe.extractors ExtractorBase)))

;; Available extractors in Boilerpipe
;; defonce prevents more than one assignment
(defonce article-extractor (ArticleExtractor/getInstance))
(defonce default-extractor (DefaultExtractor/getInstance))
;(defonce largest-context-extractor (LargestContentExtractor/getInstance))
;(defonce canola-extractor (CanolaExtractor/getInstance))
;(defonce keep-everything-extractor (KeepEverythingExtractor/getInstance))
;(defonce num-words-rules-extractor (NumWordsRulesExtractor/getInstance))

(defn get-content
  "Gets either only html source of the web page, or
  web page with extractor
  Args:
    "
  ([html]
     (get-content html article-extractor))
  ([^String html ^ExtractorBase extractor]
     (.getText extractor html)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (def article (slurp "https://help.github.com/articles/open-source-licensing"))
  (println article)
  (println(get-content article)))
