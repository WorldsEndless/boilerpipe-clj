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
  "Runs simple examples for usage of wrapper for extractors"
  [& args]
  (def article (slurp "https://help.github.com/articles/open-source-licensing"))
  (def article (slurp "http://fivethirtyeight.com/features/lionel-messi-is-impossible/"))
  (println article)
  (println (str " DEFAULT EXTRACTOR:\n\t\n" (get-content article)))
  (println (str "\n\n\n ARTICLE EXTRACTOR:\n\t\n" (get-content article article-extractor)))
  (println (str "\n\n\n LARGEST CONTEXT EXTRACTOR:\n\t\n" (get-content article largest-context-extractor)))
  (println (str "\n\n\n CANOLA EXTRACTOR:\n\t\n" (get-content article canola-extractor)))

  )
