package accelerator.server.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-08-03 11:02:20")
/** */
public final class ProjectMeta extends org.slim3.datastore.ModelMeta<accelerator.shared.model.Project> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Project, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Project, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<accelerator.shared.model.Project> name = new org.slim3.datastore.StringAttributeMeta<accelerator.shared.model.Project>(this, "name", "name");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Project, com.google.appengine.api.users.User> user = new org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Project, com.google.appengine.api.users.User>(this, "user", "user", com.google.appengine.api.users.User.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Project, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Project, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final org.slim3.datastore.CreationUser slim3_userAttributeListener = new org.slim3.datastore.CreationUser();

    private static final ProjectMeta slim3_singleton = new ProjectMeta();

    /**
     * @return the singleton
     */
    public static ProjectMeta get() {
       return slim3_singleton;
    }

    /** */
    public ProjectMeta() {
        super("Project", accelerator.shared.model.Project.class);
    }

    @Override
    public accelerator.shared.model.Project entityToModel(com.google.appengine.api.datastore.Entity entity) {
        accelerator.shared.model.Project model = new accelerator.shared.model.Project();
        model.setKey(entity.getKey());
        model.setName((java.lang.String) entity.getProperty("name"));
        model.setUser((com.google.appengine.api.users.User) entity.getProperty("user"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        accelerator.shared.model.Project m = (accelerator.shared.model.Project) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("name", m.getName());
        entity.setProperty("user", m.getUser());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        accelerator.shared.model.Project m = (accelerator.shared.model.Project) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        accelerator.shared.model.Project m = (accelerator.shared.model.Project) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        accelerator.shared.model.Project m = (accelerator.shared.model.Project) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        accelerator.shared.model.Project m = (accelerator.shared.model.Project) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
        accelerator.shared.model.Project m = (accelerator.shared.model.Project) model;
        m.setUser(slim3_userAttributeListener.prePut(m.getUser()));
    }

    @Override
    public String getSchemaVersionName() {
        return "slim3.schemaVersion";
    }

    @Override
    public String getClassHierarchyListName() {
        return "slim3.classHierarchyList";
    }

    @Override
    protected boolean isCipherProperty(String propertyName) {
        return false;
    }

    @Override
    protected void modelToJson(org.slim3.datastore.json.JsonWriter writer, java.lang.Object model, int maxDepth, int currentDepth) {
        accelerator.shared.model.Project m = (accelerator.shared.model.Project) model;
        writer.beginObject();
        org.slim3.datastore.json.Default encoder0 = new org.slim3.datastore.json.Default();
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder0.encode(writer, m.getKey());
        }
        if(m.getName() != null){
            writer.setNextPropertyName("name");
            encoder0.encode(writer, m.getName());
        }
        if(m.getUser() != null){
            writer.setNextPropertyName("user");
            encoder0.encode(writer, m.getUser());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder0.encode(writer, m.getVersion());
        }
        writer.endObject();
    }

    @Override
    protected accelerator.shared.model.Project jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        accelerator.shared.model.Project m = new accelerator.shared.model.Project();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.Default decoder0 = new org.slim3.datastore.json.Default();
        reader = rootReader.newObjectReader("key");
        m.setKey(decoder0.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("name");
        m.setName(decoder0.decode(reader, m.getName()));
        reader = rootReader.newObjectReader("user");
        m.setUser(decoder0.decode(reader, m.getUser()));
        reader = rootReader.newObjectReader("version");
        m.setVersion(decoder0.decode(reader, m.getVersion()));
        return m;
    }
}