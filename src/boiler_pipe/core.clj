(ns boiler-pipe.core
  (:gen-class)
  (:require [clojure.string :as str])
  (:import (de.l3s.boilerpipe.extractors ArticleExtractor)
           (de.l3s.boilerpipe.extractors DefaultExtractor)
           (de.l3s.boilerpipe.extractors LargestContentExtractor)
           (de.l3s.boilerpipe.extractors CanolaExtractor)
           (de.l3s.boilerpipe.extractors KeepEverythingExtractor)
           (de.l3s.boilerpipe.extractors KeepEverythingWithMinKWordsExtractor)
           (de.l3s.boilerpipe.extractors ExtractorBase)))
(def doc-num (atom 0))
;; Available extractors in Boilerpipe
;; defonce prevents more than one assignment
;http://grepcode.com/file/repo1.maven.org/maven2/de.l3s.boilerpipe/boilerpipe/1.1.0/de/l3s/boilerpipe/extractors/CommonExtractors.java#CommonExtractors
(defonce article-extractor (ArticleExtractor/getInstance))
(defonce default-extractor (DefaultExtractor/getInstance))
(defonce largest-context-extractor (LargestContentExtractor/getInstance))
(defonce canola-extractor (CanolaExtractor/getInstance))

(defn get-content
  "Gets either only html source of the web page, or
  web page with extractor
  Args:
    html(String): html source of the web page
    extractor(ExtractorBase): [optional] Extractor to get the content of
  the webpage - If not provided, default-extractor is used for extractor

  Returns:
    Content of the html(removed html tags and ideally irrelevant parts
  of the webpage such as signature and not-relevant parts)
  "
  ([html]
     (get-content html default-extractor))
  ([^String html ^ExtractorBase extractor]
     (.getText extractor html)))

; TODO: Does not work in the current format
;(defn get-title
;  "Gets the title of the article from the html source"
;  ([html]
;   (get-title html default-extractor))
;  ([^String html ^ExtractorBase extractor]
;     (.getTitle extractor html)))
(defn get-title
  "Get the title element of given html (pre-extraction)"
  [html]
  ((re-find #"<title>(.*?)</title>" html) 1))

(defn title-to-filename [title]
  (swap! doc-num inc)
  (-> (str (format "%02d" @doc-num) "_" title )
      str/trim
      str/lower-case
      (#(str/replace % #"[.:“”!~-]" ""))
      (#(str/replace % #" +" "_"))
      (#(str/replace % #"_-_" "-"))))

(defn get-files
  "Get a lazy-seq of the files in a (str dir)"
  [dir-path]
  (file-seq (clojure.java.io/file dir-path)))

(defn content-with-title
  "Produce the boiler-plate removed content preceded by the title"
  [html]
  (let [title (get-title html)
        base-content (str/trim (get-content html))
        formatted (-> base-content
                      (str/replace "\n " "")
                      (str/replace " " " ")
                      (str/replace "\n" "\n\n"))]
    (str title "\n\n" formatted)))

; if the file is an html file, pass it through content-with-title and spit it to the dir
(defn process-files
  "given a file seq, if the file is an html file, pass it through content-with-title and output to dir (with trailing slash)"
  [dir]
  (doseq [f (filter #(re-find #"\.html" (.getName %)) files)] 
    (let [html (slurp f)
          con (content-with-title html)
          ;title (get-title html)
          ;; filename  (title-to-filename title)
          ;; extension ".txt"
          ]
      con))))

(defn -main
  "Boiler-plate removal from command-line given a directory, a file, or a url"
  [& args]
  args
  ;(println (str args))
  ;; File: Process the file and output
  ;; URL: Process the file(s) and output
  ;; Directory: Process every file in the directory

  ;; Valid flags:
  ;; -n number output files
  ;; -o output file (not std-in)

  )


;; Running example:
;; ----------------
;; (def gc-files (file-seq (clojure.java.io/file "/home/torysa/Documents/Gospel_Files/Conference_Talks/2015-2")))
;; (process-files gc-files "/home/torysa/Documents/Gospel_Files/Conference_Talks/2015-2/Txt/")


;; TODO
;; 1. [ ] Simplify harvesting of html files
;; 2. [ ] automate processing for command line
;; 3. [X] Output names have a number to keep them in original order
