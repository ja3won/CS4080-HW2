module Main where

-- This record is the functional version of an object. It bundles all of the
-- operations that can be performed on one type into a single value.
data Object = Object
  { getTitle :: () -> String
  , describe :: () -> String
  }

-- The functions below are closures because they both capture the same title.
-- The title acts like the private "this" value of an object. Code that receives
-- the finished Object can use its operations without knowing how it stores data.
makeBook :: String -> Object
makeBook title = Object
  { getTitle = \() -> title
  , describe = \() -> "book called " ++ title
  }

-- A new type can be added by writing another constructor that returns an
-- Object with its own versions of getTitle and describe. Existing code would
-- still work because every new type presents the same Object interface.
main :: IO ()
main = do
  let book = makeBook "The Hobbit"
  putStrLn (getTitle book ())
  putStrLn (describe book ())