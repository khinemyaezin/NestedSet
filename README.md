# Tree Structure Implementation

This project demonstrates how to implement a tree structure using a composite pattern in Java, extending an abstract `NodeComponent` class. Below are the steps to set up the entities, repositories, service layer, and configuration required to manage a category hierarchy.

This project constructs with,
1. **NodeComponent:** `abstract class`
    - is to serve as the base class for implementing a composite pattern, which is a structural design pattern that allows you to treat individual objects (leaves) and compositions of objects (composites) uniformly.
2. **HibernateNodeRepository:** `abstract class`
    - is a repository implementation that provides operations for entities. 
4. **AbstractNodeFunctions:** `abstract class`
   -  This class is responsible for handling the construction of composite pattern classes.
5. **NodeTemplate:** `abstract class`
   - is a template class that provides a structured way to perform operations on NodeComponent class. It acts as a base class that encapsulates the common functionality required for managing hierarchical data structures.
6. **TreeBuilder:** `class`
   - is designed to provide a structured way to construct and manage tree-like hierarchical structures.
   
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
