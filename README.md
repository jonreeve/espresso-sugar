# ARCHIVED

This isn't being updated (last update was ~10yrs ago). You should definitely make your tests succinct, readable, fluent even. 
Maybe do this kind of thing, maybe do a Page-Object-Model (POM). I generally do. But these days I use Compose and thus use 
the testing utils for that (and wrap those in POMs).

# espresso-sugar
Some (syntactic) sugar for your (UI-testing) Espresso.

Why should your tests say this?

```
onView(withText("Help")).perform(click());
```

... when they could read like this?

```
clickView(withText("Help"));
```

You don't say "on door perform hold", you say "hold the door". That's perfectly possible in your tests too. Just add some sugar to your Espresso.

### Why bother?

Readable tests help us:

 * We can easily read them as specifications, and if need be even non-programmers can read and verify those specifications too.
 * When we look at a test, we can understand it as quickly and easily as possible. The seconds saved will add up, and mistakes as we parse the meaning are less likely.

To this end, it's worth making tests fluent where possible. Programmers are programmers, sure, we can read code. But we're people too!

While Espresso has improved things by miles on Robotium, the syntax can still be improved a little. That's what this little library does.
