package com.revature.minigame;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.revature.minigame.models.MonsterInstance;
import com.revature.models.CharacterModel;

public class Response {
	
	private String image;
	private String encounter;
	private final List<String> actions;
	private final List<String> localPlayers;
	
	Response() {
		this.image = "dungeon.png";
		this.encounter = "An error occured";
		this.actions = new LinkedList<String>();
		this.localPlayers = new LinkedList<String>();
	}

	public void addEncounter(final String encounter) {
		if(this.encounter.equals("An error occured"))
			this.encounter = encounter;
		else
			this.encounter += "<br>" + encounter;
	}

	public String getEncounter() {
		return this.encounter;
	}

	public List<String> getActions() {
		return this.actions;
	}

	public void addAction(final String action) {
		this.actions.add(action);
	}

	public void resetActions() {
		while(this.actions.size() > 0)
			this.actions.remove(0);
	}

	public List<String> getPlayers() {
		return this.localPlayers;
	}

	public void addPlayer(final String id) {
		this.localPlayers.add(id);
	}

	public void addPlayer(final CharacterModel model) {
		this.localPlayers.add(String.format("%s - lv %s %s %s", model.getUsername(),model.getGameData().getCharacterLevel(), model.getRace(), model.getClazz()));
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(final String image) {
		this.image = image;
	}
	
	private static String[] encounterMessages = new String[] {
		"A %s blocks your path.",
		"A %s is guarding a door.",
		"A %s stands ominously in the center of the room.",
		"A %s stares at you menacingly.",
		"A %s is looking for a fight.",
		"A %s beckons for you to step up.",
		"A %s challenges you to a fight.",
		"A %s is looking for a fight.",
		"A %s steps out of the shadows.",
		"A %s is ready to pounce.",
		"A %s roars in defiance.",
		"A %s flexes their mussels."
	};
	
	public void generateEncounter(final CharacterModel player, final MonsterInstance monster) {
		monster.setAngry(true);
		final Random random = new Random();
		final int index = random.nextInt(encounterMessages.length);
		setImage(monster.getType().getImage());
		addEncounter( String.format(encounterMessages[index], monster.getName()) );
	}
	
	private static String[] attackMessages = new String[] {
		"The %s punches you in the face, dealing %s damage.",
		"The %s waits no time getting all up in your buisness, dealing %s damage.",
		"The %s rushes into the action, dealing %s damage.",
		"The %s gets really angry, dealing %s damage.",
		"The %s lashes out, dealing %s damage.",
		"The %s is way stronger then they look, dealing %s damage.",
		"The %s gets inside your guard, dealing %s damage.",
		"The %s lands a hit, dealing %s damage.",
		"The %s can't be stopped, dealing %s damage.",
		"The %s sees right throw your defense, dealing %s damage.",
		"The %s darts forward, dealing %s damage.",
		"The %s quickly lunges, dealing %s damage."
	};

	public void generateAttack(final CharacterModel player, final MonsterInstance monster) {
		final Random random = new Random();
		setImage(monster.getType().getImage());
		
		float damageModifier = 1.0f;
		if(damageModifier > 0.5f && player.getGameData().getEffects().contains("parry")) {
			player.getGameData().getEffects().remove("parry");
			if(random.nextFloat() < 0.6f) {
				addEncounter("You parry the " + monster.getName() + "'s attack and deal 5 damage in the process!");
				monster.damage(5);
				damageModifier = 0.0f;
			} else {
				addEncounter("You fail to parry the attack, giving the monster and opening to attack!");
				damageModifier += 0.25f;
			}
		}
		if(damageModifier > 0.5f && player.getGameData().getEffects().contains("dodge")) {
			player.getGameData().getEffects().remove("dodge");
			if(random.nextFloat() < 0.5f) {
				addEncounter("You dodge the " + monster.getName() + "'s attack!");
				damageModifier = 0.0f;
			} else {
				addEncounter("You fail to dodge the attack!");
			}
		}
		if(damageModifier > 0.5f && player.getGameData().getEffects().contains("hide")) {
			player.getGameData().getEffects().remove("dodge");
			if(random.nextFloat() < 0.4f) {
				addEncounter("You hide from the " + monster.getName() + "!");
				damageModifier = 0.0f;
				monster.setAngry(false);
			} else {
				addEncounter("You try to hide, but the monster still finds you!");
			}
		}
		
		if(damageModifier > 0.5f && player.getGameData().getEffects().contains("reinforce")) {
			player.getGameData().getEffects().remove("reinforce");
			addEncounter("Your defense was reinforced, so you take less damage!");
			damageModifier -= 0.5f;
		}
		
		if(damageModifier > 0.5f && player.getGameData().getEffects().contains("protection")) {
			player.getGameData().getEffects().remove("protection");
			addEncounter("The monster attacks, but you are under the effect of a protection spell, and take no damage!");
			damageModifier = 0.0f;
		}
		
		if(damageModifier > 0.5f && player.getGameData().getEffects().contains("distract")) {
			player.getGameData().getEffects().remove("distract");
			addEncounter("The monster is too distracted by a shadow double to attack!");
			damageModifier = 0.0f;
		}
		
		if(damageModifier > 0.0f) {
			final int damage = (int) Math.ceil(monster.getDamage() * damageModifier);
			player.getGameData().modifyHealth(-damage);
			final int index = random.nextInt(attackMessages.length);
			addEncounter( String.format(attackMessages[index], monster.getName(), damage) );
		}
	}
	
	public void generatePlayerAttacks(final CharacterModel characterModel) {
		switch(characterModel.getClazz()) {
		case "fighter":
			addAction("Attack with Sword");
			addAction("Parry Attack");
			addAction("Inspire");
			break;
		case "tank":
			addAction("Attack with Spear");
			addAction("Parry Attack");
			addAction("Reinforce");
			break;
		case "mage":
			addAction("Cast Fireball");
			addAction("Attempt to Dodge");
			addAction("Cast Protection");
			break;
		case "healer":
			addAction("Attack with Staff");
			addAction("Attempt to Dodge");
			addAction("Cast Healing");
			break;
		case "assasian":
			addAction("Perform Sneakattack");
			addAction("Attempt to Hide");
			addAction("Shadow Double");
			break;
		case "marksman":
			addAction("Attack with Longbow");
			addAction("Attempt to Hide");
			addAction("Attack using Poison Arrow");
			break;
		}
		addAction("Recover");
		addAction("Run Away");
	}
	
	
	
}
