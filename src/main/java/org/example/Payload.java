package org.example;

import java.util.List;

public class Payload {

    List<Commit> commits;

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }
}
