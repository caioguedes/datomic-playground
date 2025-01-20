(ns playground.library.schema)

(def library-schema
  [{:db/ident :member/name
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc "A member's name"}
   {:db/ident :member/email
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/unique :db.unique/identity
    :db/doc "A member's email address"}
   {:db/ident :book/isbn
    :db/valueType :db.type/long
    :db/cardinality :db.cardinality/one
    :db/unique :db.unique/identity
    :db/doc "A book's ISBN-13"}
   {:db/ident :book/title
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc "A book's title"}
   {:db/ident :book/author
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc "A book's author"}
   {:db/ident :book/publish-year
    :db/valueType :db.type/long
    :db/cardinality :db.cardinality/one
    :db/doc "A book's publish year"}
   {:db/ident :loan/date
    :db/valueType :db.type/instant
    :db/cardinality :db.cardinality/one
    :db/doc "A loan's date"}
   {:db/ident :loan/due-date
    :db/valueType :db.type/instant
    :db/cardinality :db.cardinality/one
    :db/doc "A loan's due date"}
   {:db/ident :loan/member
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/one
    :db/doc "A loan's member"}
   {:db/ident :loan/books
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/many
    :db/doc "The loan's books"}])

(def library-samples
  [{:book/isbn 9781680502466
    :book/title "Programming Clojure (The Pragmatic Programmers)"
    :book/author "Miller, Alex - Halloway, Stuart - Bedra, Aaron"
    :book/publish-year 2018}
   {:book/isbn 9781680503005
    :book/title "Getting Clojure: Build Your Functional Skills One Idea at a Time"
    :book/author "Olsen, Russ"
    :book/publish-year 2018}
   {:book/isbn 9781593275914
    :book/title "Clojure for the Brave and True: Learn the Ultimate Language and Become a Better Programmer"
    :book/author "Daniel Higginbotham"
    :book/publish-year 2015}
   {:member/name "Member 1"
    :member/email "member1@example.com"}
   {:member/name "Member 2"
    :member/email "member2@example.com"}
   {:loan/member {:member/email "member2@example.com"}
    :loan/date #inst "2025-01-01"
    :loan/due-date #inst "2025-01-21"
    :loan/books [{:book/isbn 9781593275914}]}
   {:loan/member {:member/email "member1@example.com"}
    :loan/date #inst "2015-01-01"
    :loan/due-date #inst "2015-01-21"
    :loan/books [{:book/isbn 9781680502466}]}
   {:loan/member {:member/email "member2@example.com"}
    :loan/date #inst "2024-12-01"
    :loan/due-date #inst "2024-12-21"
    :loan/books [{:book/isbn 9781593275914}]}
   {:loan/member {:member/email "member2@example.com"}
    :loan/date #inst "2024-11-01"
    :loan/due-date #inst "2024-11-21"
    :loan/books [{:book/isbn 9781593275914}]}
   {:loan/member {:member/email "member1@example.com"}
    :loan/date #inst "2024-11-01"
    :loan/due-date #inst "2024-11-21"
    :loan/books [{:book/isbn 9781680503005}]}])

