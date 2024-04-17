package com.example.hustlex;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hustlex.datastructure.linksequence.LinkSequence;
import com.example.hustlex.datastructure.tree.Tree;
import com.example.hustlex.datastructure.tree.TreeInterface;
import com.example.hustlex.model.KanbanTasks;

import java.util.Date;

public class DailyTaskTracker extends AppCompatActivity {
    TreeInterface<KanbanTasks> tree;

    LinkSequence<Tree<KanbanTasks>.Node> notStartedTasksLinkSequence;
    LinkSequence<Tree<KanbanTasks>.Node> inProgressTasksLinkSequence;
    LinkSequence<Tree<KanbanTasks>.Node> completedTasksLinkSequence;

    CustomAdapterNotStarted csAdapterNotStarted;
    CustomAdapterInProgress csAdapterInProgress;
    CustomAdapterCompleted  csAdapterCompleted;

    Dialog addNewTaskDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        addNewTaskDialog = new Dialog(this);
        addNewTaskDialog.setContentView(R.layout.add_new_task_dialog);
        addNewTaskDialog.setCancelable(false);

        Button addNewTask = findViewById(R.id.btnAddNewTask);
        addNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTaskDialog.show();
            }
        });

        Button dialogCancelBtn = addNewTaskDialog.findViewById(R.id.cancel);
        Button dialogAddBtn = addNewTaskDialog.findViewById(R.id.addNew);

        dialogCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTaskDialog.dismiss();
            }
        });

        dialogAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titleText = addNewTaskDialog.findViewById(R.id.dialogAddNewTitle);
                EditText contentText = addNewTaskDialog.findViewById(R.id.dialogAddNewContent);

                String title = titleText.getText().toString();
                String content = contentText.getText().toString();

                if(title.isEmpty()){
                    Toast.makeText(DailyTaskTracker.this, "Title field is empty!", Toast.LENGTH_LONG).show();
                }else{
                    KanbanTasks newTask = new KanbanTasks(title, content, "notStarted", new Date(), null, null);
                    Tree<KanbanTasks>.Node newTreeNode = tree.createNode("task", "status", newTask);
                    notStartedTasksLinkSequence.add(newTreeNode);
                    csAdapterNotStarted.notifyItemInserted(notStartedTasksLinkSequence.length() - 1);
                    addNewTaskDialog.dismiss();
                }
            }
        });



        tree = new Tree<>();
        tree.addNode(tree.createNode("treeRoot", "root", null), "");
        tree.addNode(tree.createNode("notStarted", "status", null), "");
        tree.addNode(tree.createNode("inProgress", "status", null), "");
        tree.addNode(tree.createNode("completed", "status", null), "");

        KanbanTasks kt1 = new KanbanTasks("task-1", "content-1", "notStarted", new Date(), null, null);
        KanbanTasks kt2 = new KanbanTasks("task-2", "content-2", "notStarted", new Date(), null, null);
        KanbanTasks kt3 = new KanbanTasks("task-3", "content-3", "inProgress", new Date(), new Date(), null);
        KanbanTasks kt4 = new KanbanTasks("task-4", "content-4", "inProgress", new Date(), new Date(), null);
        KanbanTasks kt5 = new KanbanTasks("task-5", "content-5", "inProgress", new Date(), new Date(), null);

        tree.addNode(tree.createNode("task", "status", kt1), "notStarted");
        tree.addNode(tree.createNode("task", "status", kt2), "notStarted");
        tree.addNode(tree.createNode("task", "status", kt3), "inProgress");
        tree.addNode(tree.createNode("task", "status", kt4), "inProgress");
        tree.addNode(tree.createNode("task", "status", kt5), "inProgress");
//        LinkSequence<Tree<KanbanTasks>.Node> notStartedTasksLinkSequence =  tree.getChildrenOf("notStarted");
//        notStartedTasksLinkSequence.get(0)

        notStartedTasksLinkSequence =  tree.getChildrenOf("notStarted");
//        Tree<KanbanTasks>.Node node = notStartedTasksLinkSequence.get(0).;

        RecyclerView cvNotStarted = (RecyclerView) findViewById(R.id.recycleViewNotStarted);
        cvNotStarted.setLayoutManager(new LinearLayoutManager(this));
        csAdapterNotStarted = new CustomAdapterNotStarted(this, notStartedTasksLinkSequence);
        Log.d("Custom Message: ", " "+ tree.displayTree());
        cvNotStarted.setAdapter(csAdapterNotStarted);

        inProgressTasksLinkSequence =  tree.getChildrenOf("inProgress");
        RecyclerView cvInprogress = (RecyclerView) findViewById(R.id.recycleViewInProgress);
        cvInprogress.setLayoutManager(new LinearLayoutManager(this));
        csAdapterInProgress = new CustomAdapterInProgress(this, inProgressTasksLinkSequence);
        Log.d("Custom Message: ", " "+ tree.displayTree());
        cvInprogress.setAdapter(csAdapterInProgress);

        completedTasksLinkSequence =  tree.getChildrenOf("completed");
        RecyclerView cvCompleted= (RecyclerView) findViewById(R.id.recycleViewCompleted);
        cvCompleted.setLayoutManager(new LinearLayoutManager(this));
        csAdapterCompleted = new CustomAdapterCompleted (DailyTaskTracker.this, completedTasksLinkSequence);
        Log.d("Custom Message: ", " "+ tree.displayTree());
        cvCompleted.setAdapter(csAdapterCompleted);
    }

    public void updateLists(int position, String fromStatus, String toStatus){
        switch (fromStatus) {
            case "notStarted": {
                Tree<KanbanTasks>.Node toRemove = notStartedTasksLinkSequence.get(position);
//                tree.transferTo(toRemove, "notStarted", "inProgress");
                notStartedTasksLinkSequence.removeAt(position);
                csAdapterNotStarted.notifyItemRemoved(position);
                if (toStatus.equals("inProgress")) {
                    toRemove.kanbanTask.setStartTime(new Date());
                    inProgressTasksLinkSequence.add(toRemove);
                    tree.transferTo(toRemove, "notStarted", "inProgress");
                    csAdapterInProgress.notifyItemInserted(inProgressTasksLinkSequence.length() - 1);
                } else if (toStatus.equals("completed")) {
                    toRemove.kanbanTask.setDuration(0);
                    completedTasksLinkSequence.add(toRemove);
                    tree.transferTo(toRemove, "notStarted", "completed");
                    csAdapterCompleted.notifyItemInserted(completedTasksLinkSequence.length() - 1);
                }
                break;
            }
            case "inProgress": {
                Tree<KanbanTasks>.Node toRemove = inProgressTasksLinkSequence.get(position);
                inProgressTasksLinkSequence.removeAt(position);
                csAdapterInProgress.notifyItemRemoved(position);
                if (toStatus.equals("completed")) {
                    toRemove.kanbanTask.setEndTime(new Date());
                    Log.d("Custom Message - inProgressSDate: ", "" + toRemove.kanbanTask.getStartTime().getTime());
                    Log.d("Custom Message - inProgressEDate: ", "" + toRemove.kanbanTask.getEndTime().getTime());
                    Log.d("Custom Message - Duration: ", "" + ((toRemove.kanbanTask.getEndTime().getTime() - toRemove.kanbanTask.getStartTime().getTime()) / 60000));
                    toRemove.kanbanTask.setDuration(((toRemove.kanbanTask.getEndTime().getTime() - toRemove.kanbanTask.getStartTime().getTime()) / 60000));
                    completedTasksLinkSequence.add(toRemove);
                    tree.transferTo(toRemove, "inProgress", "completed");
                    csAdapterCompleted.notifyItemInserted(completedTasksLinkSequence.length() - 1);
                }
                break;
            }
            case "completed":
                Tree<KanbanTasks>.Node toRemove = completedTasksLinkSequence.get(position);
                completedTasksLinkSequence.removeAt(position);
                tree.removeNode(toRemove, "completed");
                csAdapterCompleted.notifyItemRemoved(position);
                break;
        }
        Log.d("Custom Message: Visualization of tree", tree.displayTree());
    }
}
    
}