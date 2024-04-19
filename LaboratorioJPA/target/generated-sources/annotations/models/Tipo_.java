package models;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Lenguaje;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-04-19T14:30:42", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Tipo.class)
public class Tipo_ { 

    public static volatile SingularAttribute<Tipo, String> descripcion;
    public static volatile SingularAttribute<Tipo, Integer> id;
    public static volatile SingularAttribute<Tipo, String> nombre;
    public static volatile CollectionAttribute<Tipo, Lenguaje> lenguajeCollection;

}