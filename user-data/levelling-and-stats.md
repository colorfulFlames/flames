# Levelling and Stats

As you send messages, you'll slowly gain experience. Get enough experience, and you'll level up. Level up, and your stats increase. Your stats increase, and you become more powerful, which lets you gain more experience. The cycle continues.

## Levels and Experience

Your experience is about equal to the sum of all positive message scores, before they are affected by your stats.

Each level threshold is calculated as follows:

$$
a(l+1)
$$

Where a is the Average Flames Score and l is your current level.

## Stats

Each time you level up, all of your stats have a chance of increasing, depending on their growth rate. The higher the growth rate, the more likely that stat is to increase.

The following table details each of the stats.

| Stat | Abv. | Description | Base Value | Growth Rate |
| :--- | :--- | :--- | :--- | :--- |
| Power | POW | Each positive message score is multiplied by your Power before being added. | 1 | 75% |
| Resistance | RES | Each negative message score is divided by your Resistance before being subtracted. | 1 | 50% |
| Luck | LUCK | FFI | 1 | 25% |
| Rising | RISE | FFI; Will allow you to get a higher bonus each day. | 1 | 25% |
| Carisma | CAR | FFI | 1 | 50% |

FFI: For future implementation; these stats exist and grow like any other stats, but currently have no effect.

