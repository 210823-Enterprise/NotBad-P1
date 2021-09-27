package com.revature.minigame;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.revature.minigame.models.Area;
import com.revature.minigame.models.EDirection;
import com.revature.minigame.models.GameBoard;
import com.revature.minigame.models.Position;
import com.revature.models.CharacterModel;

public class Minigame {
	
	private static GameBoard gameBoard;
	private static Map<Integer,CharacterModel> positions;
	
	public static Response generateResponse(final CharacterModel player, final String action) {
		if(player == null)
			return new Response();

		final Response response = new Response();
		
		if(gameBoard == null)
			reset();
		
		if(!positions.containsKey(player.getId()) || player.getGameData().getPos() == null) {
			player.resetGameData();
			player.getGameData().setPos(gameBoard.getStart());
			positions.put(player.getId(), player);
			if(player.getGameData().getCharacterLevel() > 1) {
				response.addEncounter("The dungeon has been completed by another player, better luck next time.");
				return response;
			}
		}
		
		final Position pos = player.getGameData().getPos();
		final Area area = gameBoard.getArea(pos);
		
		for(final Map.Entry<Integer, CharacterModel> entry: positions.entrySet()) {
			if(entry.getValue().getGameData().getPos().equals(pos) && Integer.valueOf(entry.getKey()) != player.getId())
				response.addPlayer(entry.getValue().getUsername());
		}
		
		player.getGameData().modifyMana(2);
		player.getGameData().modifyStamina(2);
		
		if(action == null) {
			if(!area.isSafe()) {
				if(area.getMonster().getAngry())
					response.generateAttack(player,area.getMonster());
				else
					response.generateEncounter(player,area.getMonster());
				response.generatePlayerAttacks(player);
			} else {
				if(pos.equals(gameBoard.getStart())) {
					response.addEncounter("You stand at the entrance of the dungeon. Where do you go?");
					if(area.getDirection(EDirection.north)) response.addAction("Go North");
					if(area.getDirection(EDirection.south)) response.addAction("Go South");
					if(area.getDirection(EDirection.east)) response.addAction("Go East");
					if(area.getDirection(EDirection.west)) response.addAction("Go West");
				} else if(pos.equals(gameBoard.getFinish())) {
					response.addEncounter("Congratulations, you have reached the end of the dungeon!");
					response.addAction("Leave Dungeon");
					if(area.getDirection(EDirection.north)) response.addAction("Go North");
					if(area.getDirection(EDirection.south)) response.addAction("Go South");
					if(area.getDirection(EDirection.east)) response.addAction("Go East");
					if(area.getDirection(EDirection.west)) response.addAction("Go West");
				} else {
					response.addEncounter("Adventure awaits, where do you go?");
					if(area.getDirection(EDirection.north)) response.addAction("Go North");
					if(area.getDirection(EDirection.south)) response.addAction("Go South");
					if(area.getDirection(EDirection.east)) response.addAction("Go East");
					if(area.getDirection(EDirection.west)) response.addAction("Go West");
					
					if(player.getClazz().equals("healer")) {
						response.addAction("Cast Healing");
					}
				}
			}
		} else {
			if(area.isSafe())
				movePlayer(action, player, response, pos);
			else
				attackResult(action, player, response, pos, area);
		}

		if(player.getGameData().getEffects().contains("healing")) {
			player.getGameData().getEffects().remove("healing");
			response.addEncounter("You have been healed!");
			player.getGameData().modifyHealth(25);
		}
		
		if(player.getGameData().getHealth() < 1) {
			response.resetActions();
			response.addEncounter("You have died :(. Better luck next time!");
			response.setImage("gravestone.png");
			player.getGameData().setPos(gameBoard.getStart());
			player.resetGameData();
		}
		return response;
	}
	
	private static void attackResult(final String action, final CharacterModel player, final Response response, final Position pos, final Area area) {
		final Random random = new Random();
		response.setImage(area.getMonster().getType().getImage());
		
		float damageModifier = 1.0f;
		if(player.getGameData().getEffects().contains("inspire")) {
			damageModifier += 0.5;
			player.getGameData().getEffects().remove("inspire");
			response.addEncounter("You were inspired to deal extra damage!");
		}

		if(player.getGameData().getEffects().contains("poison_arrow")) {
			damageModifier += 0.5;
			player.getGameData().getEffects().remove("inspire");
			response.addEncounter("The monster has been weakened by a poison arrow and takes increased damage!");
		}
		
		switch(action) {
		
		//basic attacks
		case "attack_with_sword":
			if(player.getGameData().getStamina() > 5) {
				final int damage = (int) (9 + player.getGameData().getCharacterLevel() + (2*random.nextInt(2)) * damageModifier); //10-12 + level
				area.getMonster().damage(damage);
				player.getGameData().modifyStamina(-5);
				response.addEncounter("You hit the " + area.getMonster().getName() + " with your sword for " + damage + " damage!");
			} else {
				response.addEncounter("You are you tired to attack!");
			}
			break;
		case "attack_with_spear":
			if(player.getGameData().getStamina() > 5) {
				final int damage = (int) (7 + player.getGameData().getCharacterLevel() + random.nextInt(3) * damageModifier); //8-10 + level
				area.getMonster().damage(damage);
				player.getGameData().modifyStamina(-5);
				response.addEncounter("You hit the " + area.getMonster().getName() + " with your spear for " + damage + " damage!");
			} else {
				response.addEncounter("You are you tired to attack!");
			}
			break;
		case "attack_with_staff":
			if(player.getGameData().getStamina() > 5) {
				final int damage = (int) (5 + player.getGameData().getCharacterLevel() + random.nextInt(4) * damageModifier); //6-9 + level
				area.getMonster().damage(damage);
				player.getGameData().modifyStamina(-5);
				response.addEncounter("You hit the " + area.getMonster().getName() + " with your staff for " + damage + " damage!");
			} else {
				response.addEncounter("You are you tired to attack!");
			}
			break;
		case "cast_fireball":
			if(player.getGameData().getMana() > 15) {
				final int damage = (int) (14 + player.getGameData().getCharacterLevel() + random.nextInt(6) * damageModifier); //15-20 + level
				area.getMonster().damage(damage);
				player.getGameData().modifyMana(15);
				response.addEncounter("You hit the " + area.getMonster().getName() + " with your fireball for " + damage + " damage!");
			} else {
				response.addEncounter("You are you tired to attack!");
			}
			break;
		case "perform_sneakattack":
			if(player.getGameData().getStamina() > 15) {
				int damage = 0;
				if(area.getMonster().getAngry()) {
					damage = (int) (9 + player.getGameData().getCharacterLevel() * damageModifier); //10 + level
					area.getMonster().damage(damage);
					response.addEncounter("You use sneak attack, but the " + area.getMonster().getName() + " was ready for it! Only " + damage + " damage dealt.");
				} else {
					damage = (int) (18 + (2*player.getGameData().getCharacterLevel()) * damageModifier); //20 + 2*level
					area.getMonster().damage(damage);
					response.addEncounter("You use sneak attack, catching the " + area.getMonster().getName() + " by surprise! " + damage + " Damage dealt.");
				}
				player.getGameData().modifyStamina(-15);
			} else {
				response.addEncounter("You are you tired to attack!");
			}
			break;
		case "attack_with_longbow":
			if(player.getGameData().getStamina() > 10) {
				int damage = 0;
				if(area.getMonster().getAngry()) {
					damage = (int) (7 + player.getGameData().getCharacterLevel() * damageModifier); //8 + level
					area.getMonster().damage(damage);
					response.addEncounter("You use your Longbow, but the " + area.getMonster().getName() + " is too close! Only " + damage + " damage dealt.");
				} else {
					damage = (int) (13 + (2*player.getGameData().getCharacterLevel()) * damageModifier); //15 + 2*level
					area.getMonster().damage(damage);
					response.addEncounter("You use your Longbow, hitting the " + area.getMonster().getName() + " hard before it can reach you! " + damage + " Damage dealt.");
				}
				player.getGameData().modifyStamina(-10);
			} else {
				response.addEncounter("You are you tired to attack!");
			}
			break;
		
		case "parry_attack":
			response.addEncounter("You ready yourself to parry the next attack...");
			player.getGameData().addEffect("parry");
			break;
		case "attempt_to_dodge":
			if(player.getGameData().getStamina() > 5) {
				response.addEncounter("You ready yourself to dodge the next attack...");
				player.getGameData().addEffect("dodge");
				player.getGameData().modifyStamina(-5);
			} else {
				response.addEncounter("You are you tired to dodge!");
			}
			break;
		case "attempt_to_hide":
			response.addEncounter("You attempt to hide from the " + area.getMonster().getName() + "...");
			player.getGameData().addEffect("hide");
			break;
			
		case "inspire":
			if(player.getGameData().getMana() >= 50) {
				response.addEncounter("You inspire all nearby players- their next attack deals increased damage!");
				addEffectToPlayers(pos,"inspire");
				player.getGameData().modifyMana(-50);
			} else {
				response.addEncounter("You do not have enough mana to Inspire!");
			}
			break;
		case "reinforce":
			if(player.getGameData().getMana() >= 25) {
				response.addEncounter("You reinforce all nearby players- they take less damage during their next attack!");
				addEffectToPlayers(pos,"reinforce");
				player.getGameData().modifyMana(-25);
			} else {
				response.addEncounter("You do not have enough mana to Reinforce!");
			}
			break;
		case "cast_protection":
			if(player.getGameData().getMana() >= 40) {
				response.addEncounter("You cast protection over all nearby players- they take less damage during their next attack!");
				addEffectToPlayers(pos,"protection");
				player.getGameData().modifyMana(-40);
			} else {
				response.addEncounter("You do not have enough mana to Cast Protection!");
			}
			break;
		case "cast_healing":
			if(player.getGameData().getMana() >= 25) {
				response.addEncounter("You cast healing, restoring health to all nearby players!");
				addEffectToPlayers(pos,"healing");
				player.getGameData().modifyMana(-25);
			} else {
				response.addEncounter("You do not have enough mana to Cast Healing!");
			}
			break;
		case "shadow_double":
			if(player.getGameData().getMana() >= 45) {
				response.addEncounter("You create a Shadow Double, causing the monster to become confused!");
				addEffectToPlayers(pos,"distract");
				player.getGameData().modifyMana(-45);
			} else {
				response.addEncounter("You do not have enough mana use Shadow Double!");
			}
			break;
		case "attack_using_poison_arrow":
			if(player.getGameData().getMana() >= 50) {
				response.addEncounter("You use a poison arrow, weakening the monster!");
				addEffectToPlayers(pos,"poison_arrow");
				player.getGameData().modifyMana(-50);
			} else {
				response.addEncounter("You do not have enough mana use a Poison Arrow!");
			}
			break;
			
		//generic
		case "recover":
			response.addEncounter("You do nothing to recover additional mana and stamina.");
			player.getGameData().modifyMana(8);
			player.getGameData().modifyStamina(8);
			break;
		case "run_away":
			area.getMonster().damage(1);
			if(player.getGameData().getStamina() >= 20) {
				response.addEncounter("You make a 'tactical retreat.'");
				if(area.getDirection(EDirection.north) && gameBoard.getArea(pos.transform(0, 1)).isSafe())
					player.getGameData().movePos(0, 1);
				if(area.getDirection(EDirection.south) && gameBoard.getArea(pos.transform(0, -1)).isSafe())
					player.getGameData().movePos(0, -1);
				if(area.getDirection(EDirection.east) && gameBoard.getArea(pos.transform(-1, 0)).isSafe())
					player.getGameData().movePos(-1, 0);
				if(area.getDirection(EDirection.west) && gameBoard.getArea(pos.transform(1, 0)).isSafe())
					player.getGameData().movePos(1, 0);
				player.getGameData().modifyStamina(-20);
				if(!area.isSafe())
					area.getMonster().setAngry(false);
			} else {
				response.addEncounter("You are too tired to escape!");
			}
			break;
		}
		
		if(!area.getMonster().isAlive()) {
			response.addEncounter("The monster has been slain!");
			if(player.getGameData().modifyExp(area.getMonster().getLevel()))
					response.addEncounter("You have leveled up!");
		}
	}

	private static void movePlayer(final String action, final CharacterModel player, final Response response, final Position pos) {
		switch(action) {
		case "go_north":
			response.addEncounter("You head north, wondering what's just up ahead...");
			player.getGameData().movePos(0, 1);
			break;
		case "go_south":
			response.addEncounter("You head south, pushing deeper into the dungeon...");
			player.getGameData().movePos(0, -1);
			break;
		case "go_east":
			response.addEncounter("You head east, vigilant for the next threat...");
			player.getGameData().movePos(-1, 0);
			break;
		case "go_west":
			response.addEncounter("You head west, onwards to adventure...");
			player.getGameData().movePos(1, 0);
			break;
		case "leave_dungeon":
			reset();
			break;
		case "cast_healing":
			if(player.getGameData().getMana() >= 25) {
				response.addEncounter("You cast healing, restoring health to all nearby players!");
				addEffectToPlayers(pos,"healing");
				player.getGameData().modifyMana(-25);
			} else {
				response.addEncounter("You do not have enough mana to Cast Healing!");
			}
			break;
		}
	}
	
	private static void addEffectToPlayers(final Position pos, final String effect) {
		for(final Map.Entry<Integer, CharacterModel> entry: positions.entrySet()) {
			if(entry.getValue().getGameData().getPos().equals(pos)) {
				entry.getValue().getGameData().addEffect(effect);
			}
		}
	}

	private static void reset() {
		gameBoard = new GameBoard();
		positions = new HashMap<>();
	}
	
}
