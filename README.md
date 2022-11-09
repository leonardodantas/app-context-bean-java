# APP-CONTEXT-BEAN-JAVA

<p>
Exemplo de utilização da interface ApplicationContext do Spring.
</p>

### :hammer: Pré-requisitos

Será necessário a utilização de uma IDE de sua preferência e do JDK 17.

### 🎲 Iniciando projeto pela primeira vez

```bash
# Clone este repositório
git clone https://github.com/leonardodantas/app-context-bean-java

# Inicie a aplicação com uma IDE de sua preferência

#Acesse o seguinte endereço no navegador
http://localhost:8080/swagger-ui/

```

## Proposta de desenvolvimento

<p>
O objetivo do desenvolvimento deste projeto é estudar a interface ApplicationContext do Spring e o gerenciamento de Beans
existentes em uma aplicação Spring Boot. O projeto em si é bem simples, os casos de uso criados não possuem regras de negócio e apenas logan
a execução do mesmo, para assim demonstrar de forma simples como podemos gerenciar beans em tempo de execução.
</p>

## Spring IoC Container

<p>
Umas das principais características do framework Spring Boot é o Spring IoC Container. Ele possui a responsabilidade de gerenciar 
todos os objetos existentes na aplicação, cuidando das instâncias, de seu gerenciamento e de sua alocação na memória. O container do
Spring foi construído tendo como base dois princípios, Inversão de Controle e Injeção de Dependência.
</p>

<p>
O conceito de inversão de controle se refere a transferência do controle de um objeto para algum tipo de container ou estrutura. Esse
princípio é muito utilizado no contexto de programação orientada a objetos, onde uma estrutura assume o controle do fluxo do programa.
</p>

<p>
Injeção de dependência é uma forma que podemos utilizar para implementar o princípio de IoC em nosso código. Com a injeção de dependência,
não precisamos nos preocupar em montar as dependências de nossa classe, pois ela será injetada de forma automática tendo um montador 
responsável por criar os objetos, seja com uma anotação
@Autowired, ou mesmo com a utilização de construtores. No trecho de código abaixo, podemos visualizar a injeção de objetos através de construtores.
</p>

```
    private final IRestaurantRepository restaurantRepository;
    private final IGetRestaurantOrThrowNotFound getRestaurantOrThrowNotFound;

    public ActivateDeactivateDelivery(final IRestaurantRepository restaurantRepository, final IGetRestaurantOrThrowNotFound getRestaurantOrThrowNotFound) {
        this.restaurantRepository = restaurantRepository;
        this.getRestaurantOrThrowNotFound = getRestaurantOrThrowNotFound;
    } 
```

## Spring Bean

<p>
Um Bean pode ser definido como um objeto que o Spring Container instância, monta e gerencia. Podemos definir beans
em nosso projeto de duas maneiras. A primeira é criar uma classe com a anotação @Configuration, essa anotação
vai executar todos os métodos da classe anotados com @Bean na inicialização do projeto. Dessa forma, podemos
definir nessas funções como o Spring irá construir o objeto, no trecho de código abaixo temos um exemplo.
</p>

```
@Configuration
public class ValidatorsChainBeans {

    @Bean("shouldNotContainRepeatingCharacters")
    public ShouldNotContainRepeatingCharacters shouldNotContainRepeatingCharacters() {
        return new ShouldNotContainRepeatingCharacters();
    }
```

<p>
A segunda forma existente é a utilização das anotações disponíveis no Spring para que ele entenda que a classe é um bean,
entre as anotações, temos @Component, @Service, @Controller e @Repository. No trecho de código a seguir podemos visualizar
um exemplo.
</p>

```
@Component
public class ValidatePassword implements IValidatePassword {

    private final IPasswordRepository passwordRepository;
    private final IValidatorChain validatorChain;

    public ValidatePassword(final IPasswordRepository passwordRepository, final IValidatorChain validatorChain) {
        this.passwordRepository = passwordRepository;
        this.validatorChain = validatorChain;
    }
```

<p>
Desta forma o Spring se encarrega de cuidar e gerenciar todas as instâncias da classe, bem como as suas dependências através do 
conceito de injeção de dependência. A seguir podemos ver a definição das principais anotações de beans já existentes no spring:
</p>

- @Component: É uma anotação mais genérica, para indicar que a classe em questão deve ser injetada como um bean nos consumidores
que vão fazer uso dela, normalmente utilizada para beans genéricos que não possuem regras de negócio.
- @Service: Semelhante ao @Component, é um tipo de especificação do mesmo, porém está anotação deve ser utilizada em classes que possuem regras de negócios.
- @Repository: Também é uma especificação de @Component e deve ser utilizada em classes que vão fazer operações em alguma base de dados, bem semelhante 
ao padrão DAO.

## ApplicationContext

<p>
Existem duas formas que podemos utilizar para fazer usos dos beans em nossa aplicação. A primeira como descrito anteriormente,
pode ser com a anotação @Autowired ou através de nossos construtores. A segunda forma, é a que foi utilizada nesse projeto
de estudos, com a interface ApplicationContext.
</p>

<p>
ApplicationContext é uma subinterface de BeanFactory que é o nosso acesso direto à raiz do nosso Spring Container.
Esta interface possui as seguintes funcionalidades:
</p>

- Resolução de mensagens
- Suporte à internacionalização
- Publicação de eventos e contextos específicos da camada de aplicativo


## Utilizando o ApplicationContext

<p>
Nesse projeto a interface ApplicationContext é utilizada para buscar o nossos beans e tornar a
execução de nossos casos de usos totalmente dinâmicos.
</p>

<p>
O processo se inicia em nosso controller, onde esperamos receber um parâmetro do tipo String denominado type. A seguir
temos um CURL de exemplo para o request. 
</p>

```
curl -X POST "http://localhost:8080/operation" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"amount\": 200, \"type\": \"CREDIT\"}"
```
<p>
Existem três tipos de parâmetro type válidos em nossa aplicação, e cada um deles representa um caso de uso próprio. São eles:
</p>

- CREDIT
- DEBIT
- MONEY

<p>
Durante a conversão de nossa request em um domain, buscado em nosso enum qual o tipo de operação
que vamos utilizar.
</p>

```
    CREDIT(CreditUseCase.class), DEBIT(DebitUseCase.class), MONEY(MoneyUseCase.class);
```

<p>
Sendo assim existem três casos de uso em nossa aplicação e ambos implementam a interface ITransactional:
</p>

- CreditUseCase
- DebitUseCase
- MoneyUseCase

<p>
Agora só precisamos fazer uso da função getBean da interface ApplicationContext.
</p>

```
    public void execute(final Operation operation){
        final var useCase = applicationContext.getBean(operation.getUseCase());
        useCase.execute(operation);
    }
```

<p>
Podemos verificar em nosso console qual o caso de uso que foi executado.    
</p>

```
INFO 361 --- [nio-8080-exec-5] c.b.a.c.b.usecases.impl.CreditUseCase    : Execute: CreditUseCase
```

<p>
 Desta forma podemos ter aplicações com classes bem dinâmicas e bem abstratas. Entretanto
 é preciso tomar cuidado para não elevar muito o nível de complexidade da aplicação.
</p>

## Tecnologias

<div style="display: inline_block">
  <img align="center" alt="java" src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white" />
  <img align="center" alt="spring" src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" />
  <img align="center" alt="swagger" src="https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white" />
</div>

### :sunglasses: Autor

Criado por Leonardo Rodrigues Dantas.

[![Linkedin Badge](https://img.shields.io/badge/-Leonardo-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/leonardo-rodrigues-dantas/)](https://www.linkedin.com/in/leonardo-rodrigues-dantas/)
[![Gmail Badge](https://img.shields.io/badge/-leonardordnt1317@gmail.com-c14438?style=flat-square&logo=Gmail&logoColor=white&link=mailto:leonardordnt1317@gmail.com)](mailto:leonardordnt1317@gmail.com)

## Licença

Este projeto está sob a licença MIT.


