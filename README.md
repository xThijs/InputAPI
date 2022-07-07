# InputAPI
A stand alone alternative for Spigot's Conversation API made for my other plugins.

## Example
```java
ChatInput input = new InputBuilder()
      .setExpiry(20) // The time the player has to answer a question
      .addQuestion(new Question("What is your name?").onAnswer((p, s) -> {
          p.sendMessage("Hello " + s);
      })
      ).addQuestion(new Question("Where do you live?").onAnswer((p, s) -> {
          p.sendMessage("You live in " + s);
      })
      ).onExpire((p) -> {
          p.sendMessage("You ran out of time to answer.");
      }).onFinish((p) -> {
          p.sendMessage("You finished, nice!");
      }).onCancel((p) -> {
          p.sendMessage("You cancelled.");
      }).build();

input.start(player);
```
