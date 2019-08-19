package models;

import java.util.Random;

public class ArtifactNames {

    private Random rand = new Random();

    private String[] swordNames = {"Godslayer",
            "Agatha",
            "Corruption",
            "Ghost-Forged Warblade",
            "Demonic Spellblade",
            "Cursed Steel Swiftblade",
            "Lich Gold Spellblade",
            "The Facelifter, Champion of the Phoenix",
            "Rigormortis, Longblade of Ending Hope",
            "Swan Song, Blessed Blade of Power"};
    private String[] bowNames = {"Puncture",
            "Vixen",
            "Bolt",
            "Redwood Compound Bow",
            "Maple Chord",
            "Prideful Ironbark Striker",
            "Sturdy Yew Reflex Bow",
            "Firestarter, Annihilation of Putrefaction",
            "Warsong, Incarnation of the End",
            "Death's Kiss, Prick of Eternal Justice"};
    private String[] staffNames = {"Celeste",
            "Lament",
            "Gaze of Vanity",
            "Infused Energy Staff",
            "Feral Grand Staff",
            "Wind-Forged Summerwood Staff",
            "Howling Ironbark Pole",
            "Perdition, Favor of the Shadows",
            "Stalk of Illusions, Secret of Subtlety",
            "Moonlight, Legacy of the Night Sky"};

    private String[] armourNames = {"Greatplate of Shattered Visions",
            "Vest of Distant Hells",
            "Bone Armor of Fallen Hope",
            "Iron Tunic of Hellish Nightmares",
            "Champion's Adamantite Chestguard",
            "Silent Silver Batteplate",
            "Lightning Armor of Zeal",
            "Savage Vest of the Sun",
            "Armor of Blast Protection",
            "Protector of Undoing"};

    private String[] helmNames = {"Visage of Smoldering Worlds",
            "Visage of Holy Torment",
            "Ivory Greathelm of Damned Trials",
            "Silver Greathelm of Twisted Glory",
            "Oathkeeper's Golden Visage",
            "Soul Ebon Helmet",
            "Baneful Gaze of Ends",
            "Fearful Gaze of Dreams",
            "Wit of the Nightstalker",
            "Dawn of Hell's Games"};

    public String getName(String type) {
        int randomElement = rand.nextInt(10);
        switch (type) {
            case "sword":
                return swordNames[randomElement];
            case "staff":
                return staffNames[randomElement];
            case "bow":
                return bowNames[randomElement];
            case "helm":
                return helmNames[randomElement];
            case "armour":
                return armourNames[randomElement];
        }
        return "Blah";
    }
}
