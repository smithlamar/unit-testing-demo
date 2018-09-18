**What is this code?**

The goal was to demonstrate basic unit testing in junit4 to accompany this article. The develop branch contains a partial implementation of an AccountingService which performs operations on a List of AccountEntry objects. AccountEntry's represent indiviual deposits and withdrawals (credits/debits) of monetary values. The sum of these debits and credits represent the current state of your account.

The simple  implementation can be found in the src/main/java/com/lamarjs package. The corresponding unit tests are in the scr/test/java/com/lamarjs/ package. The tests will tell you what the expected functionality of the AccountingService class is.


**First, the philosophical**

What is unit testing?

Here is an answer credited to MSDN that is a good place to start:

_"The primary goal of unit testing is to take the smallest piece of testable software in the application, isolate it
from the remainder of the code, and determine whether it behaves exactly as you expect"._

The following musings from Martin Fowler are also worth noting:

_"...there is a notion that unit tests are low-level, focusing on a small part of the software system.
Secondly unit tests are usually written these days by the programmers themselves using their regular tools -
the only difference being the use of some sort of unit testing framework. Thirdly unit tests are expected to be
significantly faster than other kinds of tests"_ - Martin Fowler - https://martinfowler.com/bliki/UnitTest.html

One of the key points above is that a unit test has a single concern, it is not something you hand off to your QA team,
but something you do as you code (whether you do it before or after you code). Lastly, they need to be lightning fast -
so no connecting to a Database or waiting for file downloads. I find it most clarifying when I think of a unit as a
"unit of work". When I use the interface of this unit, I expect a high level task to be executed to conclusion. The
conclusion (and its iterations or edge cases) is really what I am trying to test.

The reason the tests need to be fast is that you should be running them over and over again as you write new code
(or refactor old code) for a tight feedback loop that tells you that your changes are not breaking things. When your
changes have broken things, it is the job of your unit tests to give you a pretty clear idea of what functionality has
been broken, and how. Also, there will be many of these tests (hundreds, if not thousands depending on the codebase and
your coverage targets).

**Some recommendations on what to consider a unit in your java code**

Easy candidates for units in Java are methods and classes. I am of the opinion that a good place to start is with a
class from an organizational and functionality perspective. As long as your classes have singular concerns, then testing
the public interface (methods) within that class will confirm that this "unit" of functionality is working correctly.
Within your test class, you can have many tests against one or more methods in the class, so you can still go down to a
method level where it makes sense. My rule of thumb is that all public methods (excluding getters and setters) should be
tested.

**To mock or not to mock**

The short answer is, it depends. In the aforementioned Fowler article, he uses the terms sociable or solitary to
describe a class that uses its real dependencies such as other classes in your code (sociable) vs a class that has all
of its dependencies mocked (solitary). Both are acceptable approaches though the trend has been toward mocking and
near total isolation of the class under test in my experience. In either approach, it is necessary to mock external
resources that are either slow to access or non-deterministic such as a database (unless you use an embedded one like H2).

**What is mocking anyway?**

Mocking is the process of creating what Fowler calls test doubles, or "phony" instances of a class and its
functionality. Say you have a class that depends on a calculation service class to do its work. You have separately
tested the calculation service. This is an opportunity to use a mock.

To mock the calculation service, it is generally preferable to use a mocking framework such as Mockito or Powermock.
What these frameworks will do will allow you to create a "fake" instance of a class and specify what that instance's
methods should do (and return) when called. So if CalculatorService has a calculate()  method that returns an integer,
we could have our mocked instance of it return an explicit integer any time it is called by our class under test.
Generally, the mocked return value should correspond to what that service should have returned in the scenario we are
testing of the class that uses it.

Another good way to use mocks is to force negative behavior from dependencies to test how your code handles them. This can be as simple as returning a 404 or 500 from a dependent rest endpoint to having your database return bad data. This allows you to test that your app handles these scenarios gracefully.

References:

https://www.blinkingcaret.com/2016/04/27/what-exactly-is-a-unit-in-unit-testing/
https://junit.org/junit4/
https://martinfowler.com/bliki/UnitTest.html
