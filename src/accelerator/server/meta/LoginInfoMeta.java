package accelerator.server.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-08-03 11:02:20")
/** */
public final class LoginInfoMeta extends org.slim3.datastore.ModelMeta<accelerator.shared.model.LoginInfo> {

    /** */
    public final org.slim3.datastore.StringAttributeMeta<accelerator.shared.model.LoginInfo> email = new org.slim3.datastore.StringAttributeMeta<accelerator.shared.model.LoginInfo>(this, "email", "email");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.LoginInfo, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.LoginInfo, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.LoginInfo, java.lang.Boolean> loggedIn = new org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.LoginInfo, java.lang.Boolean>(this, "loggedIn", "loggedIn", boolean.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<accelerator.shared.model.LoginInfo> loginUrl = new org.slim3.datastore.StringAttributeMeta<accelerator.shared.model.LoginInfo>(this, "loginUrl", "loginUrl");

    /** */
    public final org.slim3.datastore.StringAttributeMeta<accelerator.shared.model.LoginInfo> logoutUrl = new org.slim3.datastore.StringAttributeMeta<accelerator.shared.model.LoginInfo>(this, "logoutUrl", "logoutUrl");

    /** */
    public final org.slim3.datastore.StringAttributeMeta<accelerator.shared.model.LoginInfo> nickname = new org.slim3.datastore.StringAttributeMeta<accelerator.shared.model.LoginInfo>(this, "nickname", "nickname");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.LoginInfo, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<accelerator.shared.model.LoginInfo, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final LoginInfoMeta slim3_singleton = new LoginInfoMeta();

    /**
     * @return the singleton
     */
    public static LoginInfoMeta get() {
       return slim3_singleton;
    }

    /** */
    public LoginInfoMeta() {
        super("LoginInfo", accelerator.shared.model.LoginInfo.class);
    }

    @Override
    public accelerator.shared.model.LoginInfo entityToModel(com.google.appengine.api.datastore.Entity entity) {
        accelerator.shared.model.LoginInfo model = new accelerator.shared.model.LoginInfo();
        model.setEmail((java.lang.String) entity.getProperty("email"));
        model.setKey(entity.getKey());
        model.setLoggedIn(booleanToPrimitiveBoolean((java.lang.Boolean) entity.getProperty("loggedIn")));
        model.setLoginUrl((java.lang.String) entity.getProperty("loginUrl"));
        model.setLogoutUrl((java.lang.String) entity.getProperty("logoutUrl"));
        model.setNickname((java.lang.String) entity.getProperty("nickname"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        accelerator.shared.model.LoginInfo m = (accelerator.shared.model.LoginInfo) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("email", m.getEmail());
        entity.setProperty("loggedIn", m.isLoggedIn());
        entity.setProperty("loginUrl", m.getLoginUrl());
        entity.setProperty("logoutUrl", m.getLogoutUrl());
        entity.setProperty("nickname", m.getNickname());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        accelerator.shared.model.LoginInfo m = (accelerator.shared.model.LoginInfo) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        accelerator.shared.model.LoginInfo m = (accelerator.shared.model.LoginInfo) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        accelerator.shared.model.LoginInfo m = (accelerator.shared.model.LoginInfo) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        accelerator.shared.model.LoginInfo m = (accelerator.shared.model.LoginInfo) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
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
        accelerator.shared.model.LoginInfo m = (accelerator.shared.model.LoginInfo) model;
        writer.beginObject();
        org.slim3.datastore.json.Default encoder0 = new org.slim3.datastore.json.Default();
        if(m.getEmail() != null){
            writer.setNextPropertyName("email");
            encoder0.encode(writer, m.getEmail());
        }
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder0.encode(writer, m.getKey());
        }
        writer.setNextPropertyName("loggedIn");
        encoder0.encode(writer, m.isLoggedIn());
        if(m.getLoginUrl() != null){
            writer.setNextPropertyName("loginUrl");
            encoder0.encode(writer, m.getLoginUrl());
        }
        if(m.getLogoutUrl() != null){
            writer.setNextPropertyName("logoutUrl");
            encoder0.encode(writer, m.getLogoutUrl());
        }
        if(m.getNickname() != null){
            writer.setNextPropertyName("nickname");
            encoder0.encode(writer, m.getNickname());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder0.encode(writer, m.getVersion());
        }
        writer.endObject();
    }

    @Override
    protected accelerator.shared.model.LoginInfo jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        accelerator.shared.model.LoginInfo m = new accelerator.shared.model.LoginInfo();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.Default decoder0 = new org.slim3.datastore.json.Default();
        reader = rootReader.newObjectReader("email");
        m.setEmail(decoder0.decode(reader, m.getEmail()));
        reader = rootReader.newObjectReader("key");
        m.setKey(decoder0.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("loggedIn");
        m.setLoggedIn(decoder0.decode(reader, m.isLoggedIn()));
        reader = rootReader.newObjectReader("loginUrl");
        m.setLoginUrl(decoder0.decode(reader, m.getLoginUrl()));
        reader = rootReader.newObjectReader("logoutUrl");
        m.setLogoutUrl(decoder0.decode(reader, m.getLogoutUrl()));
        reader = rootReader.newObjectReader("nickname");
        m.setNickname(decoder0.decode(reader, m.getNickname()));
        reader = rootReader.newObjectReader("version");
        m.setVersion(decoder0.decode(reader, m.getVersion()));
        return m;
    }
}