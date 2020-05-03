package ch.rmbi.melspass.room;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;


@Entity(tableName = "Setup_tbl",
        foreignKeys = @ForeignKey(entity = Pass.class,
                parentColumns = "id",
                childColumns = "ftp_pass_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.SET_NULL))
public class Setup implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private long id;

    private boolean localBackup;
    private boolean ftpBackup;
    @Nullable
    private Long ftp_pass_id;

    private String ftpRemoteFolder ;

    public String getFtpRemoteFolder() {
        return ftpRemoteFolder;
    }

    public void setFtpRemoteFolder(String ftpRemoteFolder) {
        this.ftpRemoteFolder = ftpRemoteFolder;
    }

    public String getCryptoKey() {
        return cryptoKey;
    }

    public void setCryptoKey(String cryptoKey) {
        this.cryptoKey = cryptoKey;
    }

    private String cryptoKey ;


    public Setup(boolean localBackup, boolean ftpBackup, @Nullable Long ftp_pass_id, String ftpRemoteFolder, String cryptoKey, Long lastChange) {
        this.localBackup = localBackup;
        this.ftpBackup = ftpBackup;
        this.ftp_pass_id = ftp_pass_id;
        this.ftpRemoteFolder = ftpRemoteFolder;
        this.cryptoKey = cryptoKey;
        this.lastChange = lastChange;
    }


    public void setLastChange(Long lastChange) {
        this.lastChange = lastChange;
    }

    private Long lastChange ;
    public Long getLastChange() {
        return lastChange;
    }

    public Date getLastChangeDate() {
        return new Date(lastChange);
    }

    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange.getTime();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isLocalBackup() {
        return localBackup;
    }

    public void setLocalBackup(boolean localBackup) {
        this.localBackup = localBackup;
    }

    public boolean isFtpBackup() {
        return ftpBackup;
    }

    public void setFtpBackup(boolean ftpBackup) {
        this.ftpBackup = ftpBackup;
    }

    public Long getFtp_pass_id() {
        return ftp_pass_id;
    }

    public void setFtp_pass_id(Long ftp_pass_id) {
        this.ftp_pass_id = ftp_pass_id;
    }

}
