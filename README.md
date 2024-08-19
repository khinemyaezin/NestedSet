# Tree Structure Implementation

This project demonstrates how to implement a tree structure using a composite pattern in Java, extending an abstract `NodeComponent` class. Below are the steps to set up the entities, repositories, service layer, and configuration required to manage a category hierarchy.

## 1. Create the Category Entity

First, create a `Category` entity that extends the `NodeComponent` abstract class. The following annotations are required: `@Id`, `@NameColumn`, `@LeftColumn`, `@RightColumn`, and `@DepthColumn`.

```java
@Entity
@Table(name = "category")
public class Category extends NodeComponent {
    @Id
    private Long id;
    @NameColumn
    private String name;
    @LeftColumn
    private Integer lft;
    @RightColumn
    private Integer rgt;
    @DepthColumn
    private Integer depth;
}
```

## 2. Create Repositories
Next, create the necessary repository classes for handling data operations.

```java
@Repository
public class HibernateCategoryRepository extends HibernateNodeRepository<Category,Long>{

    public HibernateCategoryRepository(EntityManager entityManager) {
        super(Category.class, entityManager);
    }
}
@Repository
public interface JpaCategoryRepository extends JpaNodeRepository<Category,Long> {}
```

## 3. Create `CategoryLeaf` and `CategoryComposite` Classes
Create the `CategoryLeaf` and `CategoryComposite` classes, both extending `NodeComponent`. These classes represent the leaf nodes and composite nodes of the category tree, respectively.

```java
public class CategoryLeaf extends NodeComponent {
    private Long id;
    private String name;
    private Integer lft;
    private Integer rgt;
    private Integer depth;
    private NodeComponent parent;

    // getter, setter
}
public class CategoryComposite extends NodeComponent {
    private Long id;
    private String name;
    private Integer lft;
    private Integer rgt;
    private Integer depth;
    private Set<NodeComponent> children = new HashSet<>();
    private NodeComponent parent;

    // getter, setter
}
```

## 4. Create NodeComponentFactory to Support Leaf and Composite Classes

Implement a factory class to create instances of CategoryLeaf and CategoryComposite.

```java
public class CategoryComponentFactory implements NodeComponentFactory {

    public NodeComponent createCompositeNodeComponent() {
        return new CategoryComposite();
    }

    public NodeComponent createLeafNodeComponent() {
        return new CategoryLeaf();
    }
}
```

## 5. Configure the Application

Create a configuration class to define TreeBuilder bean for Node functions.

```java
@Configuration
public class Configuration {
    @Bean
    @Primary
    public TreeBuilder getCategoryTreeBuilder(CategoryComponentFactory factory){
        return new TreeBuilderImpl(factory);
    }
}
```

## 6. Create the Service Layer

Implement the service layer by extending the AbstractNodeFunctions class and creating a service for category operations.

```java
@Component
public class CategoryFunctions extends AbstractNodeFunctions<Category,Long>{

    public CategoryFunctions(NodeRepository<Category, Long> nodeRepository, JpaNodeRepository<Category, Long> jpaNodeRepository, TreeBuilder builder) {
       super(nodeRepository,jpaNodeRepository, builder);
    }
}
```

Then, create the CategoryService class to handle business logic related to categories.

```java
@Service
public class CategoryService extends NodeTemplate<Category, Long>{
    private final CategoryFunctions categoryFunctions;

    public CategoryServiceImpl(HibernateCategoryRepository hibernateRepository, JpaCategoryRepository jpaRepository, CategoryFunctions nodeFunctions) {
        super(hibernateRepository, jpaRepository);
        this.categoryFunctions = nodeFunctions;
    }
}
```
# Conclusion
By following these steps, you can create a robust category tree structure using the composite pattern in Java. The CategoryService class encapsulates the business logic, making it easier to manage and manipulate hierarchical data.
