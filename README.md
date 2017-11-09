# agar.io
agar.io game for ICS 111 Project 3
/*
 * Rules:
 * 1. When the Player eats a DOT, increase Player's mass
 * 2. When the Player collides with a BLOB:
 *  - If the Player's mass < a Blob's mass, then the Player gets eaten by that Blob (Game Over)
 *  - If the Player's mass > a Blob's mass, then the Player eats that Blob and increase Player's mass + that Blob's mass
 * 3. When a Virus collides with a:
 *   - Player:
 *     * If the Player's mass < Virus's mass, then the Virus does nothing
 *     * If the Player's mass > Virus's mass, then the Virus eats the Player (Game Over)
 *   - Blob:
 *     * If the Blob's mass < Virus's mass, then the Virus does nothing
 *     * If the Blob's mass > Virus's mass, then the Virus eats the Blob
 * 4. When a BLOB eats a DOT, increase that BLOB's mass
 * 5. When a BLOB collides with another BLOB:
 *  - If the Blob's mass < the other Blob's mass, then the Blob gets eaten by the other Blob
 *  - If the Blob's mass > the other Blob's mass, then the Blob eats that other Blob and increase the Blob's mass + that other Blob's mass
 * 6.The game runs forever until:
 *  - LOSE GAME: When the Player gets eaten by a BLOB or a VIRUS
 *  - WIN GAME: When the Player has eaten all the other Blobs
 */
