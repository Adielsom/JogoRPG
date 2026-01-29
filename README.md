
                                                                    **DUNGEON SURVIVOR Survivor**


**Dungeon Survivor** é um jogo de aventura e ação 2D estilo Top-Down (RPG de Masmorra), desenvolvido inteiramente em **Java** puro, utilizando a biblioteca **Swing** e **AWT**. O jogador assume o papel de um valente cavaleiro que deve atravessar labirintos perigosos, enfrentar monstros clássicos e derrotar o temível Dragão Rei.

---

## 🎯 Objetivo do Jogo
O objetivo principal é sobreviver a três níveis distintos de dificuldade crescente:
1. **Nível 1 (Floresta Sombria):** Coletar chaves e desviar de Slimes básicos para abrir o portal inicial.
2. **Nível 2 (Catacumbas):** Enfrentar um exército de esqueletos arqueiros com IA de perseguição e ataques à distância.
3. **Nível 3 (Câmara do Dragão):** Derrotar o Boss Final em uma arena fechada para conquistar a vitória definitiva.

---

## 🚀 Funcionalidades Principais

### ⚔️ Sistema de Combate e Movimentação
* **Movimentação Fluida:** Sistema de colisão preditivo que impede que o jogador fique travado em paredes ou portas.
* **Ataque de Curto Alcance:** Animação de espada com detecção de impacto em monstros.
* **Ataques à Distância:** O jogador pode disparar projéteis (Fireballs) contra os inimigos.

### 🧠 Inteligência Artificial (IA)
* **Monstros Dinâmicos:** Slimes com movimento aleatório e Esqueletos que perseguem o jogador quando ele entra no raio de visão.
* **Boss Final (Dragão Rei):** IA avançada que sempre encara o jogador e utiliza um ataque em leque (Triple Fire Shot).

### 🎨 Interface e Som
* **UI Customizada:** Tela de título, seleção de música, mapa de níveis e tela de instruções com background personalizado.
* **Barra de Vida do Boss:** Exibição dinâmica da vida do Dragão durante o confronto final.
* **Sistema de Áudio:** Música tema que inicia automaticamente e efeitos sonoros para ataques, itens e danos.

---

                                                                *🕹️ Controles*
| Tecla | Ação |
| :--- | :--- |
| **W, A, S, D** ou **Setas** | Movimentação do Cavaleiro |
| **Enter** | Ataque com Espada |
| **L** | Lançar Projétil (Fireball) |
| **P** | Pausar o Jogo |
| **Esc** | Voltar ao Menu / Sair |

---
                                                               *🛠️ Tecnologias Utilizadas*

**Linguagem:** Java (JDK 25 ou superior sugerido).
**Gráficos:** Java Swing & AWT (Renderização via `paintComponent`). 
**Arquitetura:** Orientação a Objetos (POO) com sistema de Game Loop baseado em `Thread` (60 FPS).
**Gerenciamento de Recursos:** `ImageIO` para manipulação de sprites e `Clip` para áudio.
