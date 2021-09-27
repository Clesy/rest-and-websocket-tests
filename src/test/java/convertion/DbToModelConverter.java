package convertion;

import domain.achievements.*;
import domain.card.Card;
import domain.card.UserCardData;
import domain.card.UserCardDb;
import domain.lootboxes.*;
import domain.spin.SpinData;
import domain.spin.SpinDb;
import domain.task.CurrentTaskList;
import domain.task.Task;
import domain.task.TaskDb;
import utils.InsensitiveOrderList;

import java.util.ArrayList;
import java.util.List;

public class DbToModelConverter {

    private DbToModelConverter() {
    }

    public static CurrentTaskList toCurrentTaskList(List<TaskDb> allTasksDb, Integer status) {
        List<Task> expectedTasks = new ArrayList<>();
        Long taskTime = null;

        for (TaskDb taskDb : allTasksDb) {
            expectedTasks.add(new Task(taskDb.taskId(), taskDb.taskRewardId(), taskDb.rewardAmount()));
            if (taskTime == null) {
                taskTime = taskDb.taskTime();
            }
        }
        return new CurrentTaskList(expectedTasks, taskTime, status);
    }

    public static AchievementsList toAchievementsList(List<AchievementDb> allAchievementsDb, int status) {
        InsensitiveOrderList<Achievement> expectedAchievements = new InsensitiveOrderList<>();

        for (AchievementDb achievementDb : allAchievementsDb) {
            InsensitiveOrderList<Description> achievementDescriptions = new InsensitiveOrderList<>();
            InsensitiveOrderList<GameEvent> gameEvents = new InsensitiveOrderList<>();

            expectedAchievements.add(new Achievement(
                    achievementDb.key(),
                    achievementDb.rewardType(),
                    achievementDb.rewardAmount(),
                    achievementDescriptions,
                    gameEvents));

            gameEvents.add(new GameEvent(achievementDb.eventKey(),
                    achievementDb.eventLifeType(),
                    achievementDb.eventCount().doubleValue()));

            achievementDescriptions.add((new Description("English", achievementDb.en())));
            achievementDescriptions.add((new Description("Russian", achievementDb.ru())));
        }
        return new AchievementsList(expectedAchievements, status);
    }

    public static UserAchievementList toUserAchievementList(List<UserAchievementDb> allUserAchievementDb, Integer status) {
        InsensitiveOrderList<String> expectedAchievement = new InsensitiveOrderList<>();

        for (UserAchievementDb userAchievementDb : allUserAchievementDb) {
            expectedAchievement.add(userAchievementDb.achievementId());
        }
        return new UserAchievementList(expectedAchievement, status);
    }

    public static SpinData toSpinData(List<SpinDb> allSpinDb, Integer status) {
        Long data = null;
        Integer amount = null;

        for (SpinDb spinDb : allSpinDb) {
            if (data == null) data = spinDb.spinDate();
            if (amount == null) amount = spinDb.amount();
        }
        return new SpinData(data, amount, status);
    }

    public static UserCardData userCard(List<UserCardDb> allCardDb, int status) {
        InsensitiveOrderList<Card> expectedCard = new InsensitiveOrderList<>();

        for (UserCardDb userCardDb : allCardDb) {
            expectedCard.add(new Card(
                    userCardDb.cardId(), userCardDb.experience(), userCardDb.level()));
        }
        return new UserCardData(expectedCard, status);
    }

    public static InsensitiveOrderList<CooldownedLootbox> toCooldownedLootbox(
            List<UsersCooldownedLootboxDb> allCooldownedLootbox) {

        InsensitiveOrderList<CooldownedLootbox> cooldownedLootboxes = new InsensitiveOrderList<>();

        for (UsersCooldownedLootboxDb cooldownedLootboxDb : allCooldownedLootbox) {

            cooldownedLootboxes.add(new CooldownedLootbox(cooldownedLootboxDb.lootboxId(),
                    cooldownedLootboxDb.openedAmount(),
                    cooldownedLootboxDb.lastOpenTime()));
        }
        return cooldownedLootboxes;
    }

    public static InsensitiveOrderList<UsualLootbox> toUsualLootbox(List<UsersUsualLootboxDb> allUsualLootbox) {

        InsensitiveOrderList<UsualLootbox> usualLootboxes = new InsensitiveOrderList<>();

        for (UsersUsualLootboxDb usersUsualLootboxDb : allUsualLootbox) {
            usualLootboxes.add(new UsualLootbox(usersUsualLootboxDb.lootboxId(), usersUsualLootboxDb.amount()));
        }
        return usualLootboxes;
    }

    public static LootboxInfo toLootboxInfo(List<UsersUsualLootboxDb> allUsualLootbox,
                                            List<UsersCooldownedLootboxDb> allCooldownedLootbox, int status) {

        return new LootboxInfo(toCooldownedLootbox(allCooldownedLootbox), toUsualLootbox(allUsualLootbox), status);
    }
}
