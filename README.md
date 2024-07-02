
**Isabela Lima da Cunha**

**Informações Importantes**

No método original do Quicksort, a escolha do pivô estava causando stack overflow, possivelmente devido a uma má escolha do pivô 
em casos onde o array já estava quase ordenado ou quando havia muitos elementos iguais. Para resolver este problema,
foi adotada a estratégia da mediana de três para escolher um pivô mais estável, reduzindo assim a probabilidade de 
ocorrer stack overflow.
