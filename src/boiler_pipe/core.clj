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

(defn -main
  "Runs simple examples for usage of wrapper for extractors"
  [& args]
  ; default extractor is better
  (def vox-article "http://www.vox.com/xpress/2014/9/25/6843509/income-distribution-recoveries-pavlina-tcherneva")
  ; article extractor is better
  (def fivethirtyeight-article "http://fivethirtyeight.com/features/lionel-messi-is-impossible/")
  ; canola extractor seems to be better
  (def thenextweb-article "http://thenextweb.com/microsoft/2014/09/23/microsoft-launches-online-services-bug-bounty-program-includes-office-365-rewards-starting-500/")
  ; article extractor is better
  (def venturebeat-article "http://venturebeat.com/2014/09/23/microsoft-and-godaddy-want-small-businesses-to-get-online-today/")
  ; default extractor is better
  (def techcrunch-article "http://techcrunch.com/2014/09/10/microsoft-held-in-contempt-as-it-battles-a-domestic-search-warrant-demanding-overseas-data/?ncid=rss")
  ; article extractor is better
  (def arstechnica-article "http://arstechnica.com/tech-policy/2014/09/microsoft-agrees-to-contempt-order-so-e-mail-privacy-case-can-be-appealed/")
  ; article extractor is better
  (def reuters-article "http://www.reuters.com/article/2014/09/04/us-getty-images-microsoft-lawsuit-idUSKBN0GZ2B720140904")
  ; article extractor is better
  (def theverge-article "http://www.theverge.com/2014/8/8/5984625/google-microsoft-others-backing-facebook-in-fight-for-user-privacy")
  ; Gigaom does not accept any part of article extraction methods => Need to find a way to get the content in a better way
  (def gigaom-article "http://gigaom.com/2014/06/30/microsoft-and-insteon-team-up-with-a-retail-smart-home-push/")
  ; article extractor is better
  (def wsj-article "http://online.wsj.com/article/PR-CO-20140629-900500.html")
  ; default extractor is better
  (def spoke-intel-article "http://www.spokeintel.com/tb/company/4dba42df91a3435dcd00008f/Microsoft+and+SAP+expand+a+deal+%28May-2014%29/http%3A%2F%2Fwww.bizjournals.com%2Fseattle%2Fblog%2Ftechflash%2F2014%2F05%2Fmicrosoft-and-sap-expand-a-deal.html")
  ; default extractor is better
  (def mashable-article "http://mashable.com/2014/04/23/microsoft-tech-startup-hotline/?utm_campaign=Mash-Prod-RSS-Feedburner-All-Partial&utm_cid=Mash-Prod-RSS-Feedburner-All-Partial&utm_medium=feed&utm_source=feedly&utm_reader=feedly")

  ; (def article (slurp vox-article))
  (def articles [vox-article fivethirtyeight-article thenextweb-article venturebeat-article
                 techcrunch-article arstechnica-article reuters-article theverge-article
                 gigaom-article wsj-article spoke-intel-article mashable-article])
  (doseq [[ii url](map-indexed vector articles)]
    ; process the ii - url
    (println (get-content (slurp url)))
    (println ii url))
  )
