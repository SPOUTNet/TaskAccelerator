package accelerator.server.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-08-03 11:02:20")
/** */
public final class TagMeta extends org.slim3.datastore.ModelMeta<accelerator.shared.model.Tag> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Tag, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Tag, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<accelerator.shared.model.Tag> name = new org.slim3.datastore.StringAttributeMeta<accelerator.shared.model.Tag>(this, "name", "name");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Tag, com.google.appengine.api.users.User> user = new org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Tag, com.google.appengine.api.users.User>(this, "user", "user", com.google.appengine.api.users.User.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Tag, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Tag, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final org.slim3.datastore.CreationUser slim3_userAttributeListener = new org.slim3.datastore.CreationUser();

    private static final TagMeta slim3_singleton = new TagMeta();

    /**
     * @return the singleton
     */
    public static TagMeta get() {
       return slim3_singleton;
    }

    /** */
    public TagMeta() {
        super("Tag", accelerator.shared.model.Tag.class);
    }

    @Override
    public accelerator.shared.model.Tag entityToModel(com.google.appengine.api.datastore.Entity entity) {
        accelerator.shared.model.Tag model = new accelerator.shared.model.Tag();
        model.setKey(entity.getKey());
        model.setName((java.lang.String) entity.getProperty("name"));
        model.setUser((com.google.appengine.api.users.User) entity.getProperty("user"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        accelerator.shared.model.Tag m = (accelerator.shared.model.Tag) model;
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
        accelerator.shared.model.Tag m = (accelerator.shared.model.Tag) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        accelerator.shared.model.Tag m = (accelerator.shared.model.Tag) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        accelerator.shared.model.Tag m = (accelerator.shared.model.Tag) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        accelerator.shared.model.Tag m = (accelerator.shared.model.Tag) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
        accelerator.shared.model.Tag m = (accelerator.shared.model.Tag) model;
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
        accelerator.shared.model.Tag m = (accelerator.shared.model.Tag) model;
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
    protected accelerator.shared.model.Tag jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        accelerator.shared.model.Tag m = new accelerator.shared.model.Tag();
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