package accelerator.server.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-08-03 11:02:20")
/** */
public final class TaskMeta extends org.slim3.datastore.ModelMeta<accelerator.shared.model.Task> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Task, java.lang.Boolean> completed = new org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Task, java.lang.Boolean>(this, "completed", "completed", java.lang.Boolean.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Task, java.util.Date> createdDate = new org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Task, java.util.Date>(this, "createdDate", "createdDate", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Task, java.util.Date> dueDate = new org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Task, java.util.Date>(this, "dueDate", "dueDate", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Task, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Task, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<accelerator.shared.model.Task> name = new org.slim3.datastore.StringAttributeMeta<accelerator.shared.model.Task>(this, "name", "name");

    /** */
    public final org.slim3.datastore.StringAttributeMeta<accelerator.shared.model.Task> note = new org.slim3.datastore.StringAttributeMeta<accelerator.shared.model.Task>(this, "note", "note");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Task, com.google.appengine.api.datastore.Key> project = new org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Task, com.google.appengine.api.datastore.Key>(this, "project", "project", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CollectionAttributeMeta<accelerator.shared.model.Task, java.util.List<com.google.appengine.api.datastore.Key>, com.google.appengine.api.datastore.Key> tags = new org.slim3.datastore.CollectionAttributeMeta<accelerator.shared.model.Task, java.util.List<com.google.appengine.api.datastore.Key>, com.google.appengine.api.datastore.Key>(this, "tags", "tags", java.util.List.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Task, com.google.appengine.api.users.User> user = new org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Task, com.google.appengine.api.users.User>(this, "user", "user", com.google.appengine.api.users.User.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Task, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.Task, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final org.slim3.datastore.CreationDate slim3_createdDateAttributeListener = new org.slim3.datastore.CreationDate();

    private static final org.slim3.datastore.CreationUser slim3_userAttributeListener = new org.slim3.datastore.CreationUser();

    private static final TaskMeta slim3_singleton = new TaskMeta();

    /**
     * @return the singleton
     */
    public static TaskMeta get() {
       return slim3_singleton;
    }

    /** */
    public TaskMeta() {
        super("Task", accelerator.shared.model.Task.class);
    }

    @Override
    public accelerator.shared.model.Task entityToModel(com.google.appengine.api.datastore.Entity entity) {
        accelerator.shared.model.Task model = new accelerator.shared.model.Task();
        model.setCompleted((java.lang.Boolean) entity.getProperty("completed"));
        model.setCreatedDate((java.util.Date) entity.getProperty("createdDate"));
        model.setDueDate((java.util.Date) entity.getProperty("dueDate"));
        model.setKey(entity.getKey());
        model.setName((java.lang.String) entity.getProperty("name"));
        model.setNote((java.lang.String) entity.getProperty("note"));
        model.setProject((com.google.appengine.api.datastore.Key) entity.getProperty("project"));
        model.setTags(toList(com.google.appengine.api.datastore.Key.class, entity.getProperty("tags")));
        model.setUser((com.google.appengine.api.users.User) entity.getProperty("user"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        accelerator.shared.model.Task m = (accelerator.shared.model.Task) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("completed", m.getCompleted());
        entity.setProperty("createdDate", m.getCreatedDate());
        entity.setProperty("dueDate", m.getDueDate());
        entity.setProperty("name", m.getName());
        entity.setProperty("note", m.getNote());
        entity.setProperty("project", m.getProject());
        entity.setProperty("tags", m.getTags());
        entity.setProperty("user", m.getUser());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        accelerator.shared.model.Task m = (accelerator.shared.model.Task) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        accelerator.shared.model.Task m = (accelerator.shared.model.Task) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        accelerator.shared.model.Task m = (accelerator.shared.model.Task) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        accelerator.shared.model.Task m = (accelerator.shared.model.Task) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
        accelerator.shared.model.Task m = (accelerator.shared.model.Task) model;
        m.setCreatedDate(slim3_createdDateAttributeListener.prePut(m.getCreatedDate()));
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
        accelerator.shared.model.Task m = (accelerator.shared.model.Task) model;
        writer.beginObject();
        org.slim3.datastore.json.Default encoder0 = new org.slim3.datastore.json.Default();
        if(m.getCompleted() != null){
            writer.setNextPropertyName("completed");
            encoder0.encode(writer, m.getCompleted());
        }
        if(m.getCreatedDate() != null){
            writer.setNextPropertyName("createdDate");
            encoder0.encode(writer, m.getCreatedDate());
        }
        if(m.getDueDate() != null){
            writer.setNextPropertyName("dueDate");
            encoder0.encode(writer, m.getDueDate());
        }
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder0.encode(writer, m.getKey());
        }
        if(m.getName() != null){
            writer.setNextPropertyName("name");
            encoder0.encode(writer, m.getName());
        }
        if(m.getNote() != null){
            writer.setNextPropertyName("note");
            encoder0.encode(writer, m.getNote());
        }
        if(m.getProject() != null){
            writer.setNextPropertyName("project");
            encoder0.encode(writer, m.getProject());
        }
        if(m.getTags() != null){
            writer.setNextPropertyName("tags");
            writer.beginArray();
            for(com.google.appengine.api.datastore.Key v : m.getTags()){
                encoder0.encode(writer, v);
            }
            writer.endArray();
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
    protected accelerator.shared.model.Task jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        accelerator.shared.model.Task m = new accelerator.shared.model.Task();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.Default decoder0 = new org.slim3.datastore.json.Default();
        reader = rootReader.newObjectReader("completed");
        m.setCompleted(decoder0.decode(reader, m.getCompleted()));
        reader = rootReader.newObjectReader("createdDate");
        m.setCreatedDate(decoder0.decode(reader, m.getCreatedDate()));
        reader = rootReader.newObjectReader("dueDate");
        m.setDueDate(decoder0.decode(reader, m.getDueDate()));
        reader = rootReader.newObjectReader("key");
        m.setKey(decoder0.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("name");
        m.setName(decoder0.decode(reader, m.getName()));
        reader = rootReader.newObjectReader("note");
        m.setNote(decoder0.decode(reader, m.getNote()));
        reader = rootReader.newObjectReader("project");
        m.setProject(decoder0.decode(reader, m.getProject()));
        reader = rootReader.newObjectReader("tags");
        {
            java.util.ArrayList<com.google.appengine.api.datastore.Key> elements = new java.util.ArrayList<com.google.appengine.api.datastore.Key>();
            org.slim3.datastore.json.JsonArrayReader r = rootReader.newArrayReader("tags");
            if(r != null){
                reader = r;
                int n = r.length();
                for(int i = 0; i < n; i++){
                    r.setIndex(i);
                    com.google.appengine.api.datastore.Key v = decoder0.decode(reader, (com.google.appengine.api.datastore.Key)null)                    ;
                    if(v != null){
                        elements.add(v);
                    }
                }
                m.setTags(elements);
            }
        }
        reader = rootReader.newObjectReader("user");
        m.setUser(decoder0.decode(reader, m.getUser()));
        reader = rootReader.newObjectReader("version");
        m.setVersion(decoder0.decode(reader, m.getVersion()));
        return m;
    }
}