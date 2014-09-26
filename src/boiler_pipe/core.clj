(ns boiler-pipe.core
  (:gen-class)
  (:import (de.l3s.boilerpipe.extractors ArticleExtractor)
           (de.l3s.boilerpipe.extractors DefaultExtractor)
           (de.l3s.boilerpipe.extractors LargestContentExtractor)
           (de.l3s.boilerpipe.extractors CanolaExtractor)
           (de.l3s.boilerpipe.extractors KeepEverythingExtractor)
           (de.l3s.boilerpipe.extractors KeepEverythingWithMinKWordsExtractor)
           (de.l3s.boilerpipe.extractors ExtractorBase)))

;; Available extractors in Boilerpipe
;; defonce prevents more than one assignment
(defonce article-extractor (ArticleExtractor/getInstance))
(defonce default-extractor (DefaultExtractor/getInstance))
(defonce largest-context-extractor (LargestContentExtractor/getInstance))
(defonce canola-extractor (CanolaExtractor/getInstance))

;; Due  to version, the followings do not work (may require 1.2)
;(defonce keep-everything-extractor (KeepEverythingExtractor/getInstance))
;(defonce num-words-rules-extractor (NumWordsRulesExtractor/getInstance))
;(defonce keep-everything-with-min-k-words-extractor (KeepEverythingWithMinKWordsExtractor/getInstance))

(defn get-content
  "Gets either only html source of the web page, or
  web page with extractor
  Args:
    "
  ([html]
     (get-content html default-extractor))
  ([^String html ^ExtractorBase extractor]
     (.getText extractor html)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (def article (slurp "https://help.github.com/articles/open-source-licensing"))
  (def article (slurp "http://fivethirtyeight.com/features/lionel-messi-is-impossible/"))
  (println article)
  (println (str " DEFAULT EXTRACTOR:\n\t\n" (get-content article)))
  (println (str "\n\n\n ARTICLE EXTRACTOR:\n\t\n" (get-content article article-extractor)))
  (println (str "\n\n\n LARGEST CONTEXT EXTRACTOR:\n\t\n" (get-content article largest-context-extractor)))
  (println (str "\n\n\n CANOLA EXTRACTOR:\n\t\n" (get-content article canola-extractor)))

;  (println (str "\n\n\n KEEP EVERYTHING EXTRACTOR:\n\t\n" (get-content article keep-everything-extractor)))
;  (println (str "\n\n\n NUM WORDS RULES EXTRACTOR:\n\t\n" (get-content article num-words-rules-extractor)))
;  (println (str "\n\n\n KEEP EVERYTHING WITH MINIMUM K WORDS EXTRACTOR:\n\t\n" (get-content article keep-everything-with-min-k-words-extractor)))
  )
