
```
λ git diff
diff --git a/cpp/ql/src/semmle/code/cpp/models/Models.qll b/cpp/ql/src/semmle/code/cpp/models/Models.qll
index f2612d69b8..3dee750ff1 100644
--- a/cpp/ql/src/semmle/code/cpp/models/Models.qll
+++ b/cpp/ql/src/semmle/code/cpp/models/Models.qll
@@ -23,3 +23,5 @@ private import implementations.StdString
 private import implementations.Swap
 private import implementations.GetDelim
 private import implementations.SmartPointer
+
+// private import implementations.CustomMemcpy
hac425@LAPTOP-CQ9O187I /d/SCA/vscode-codeql-starter/ql ((545451e602...))
λ git status
HEAD detached at 545451e602
Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git checkout -- <file>..." to discard changes in working directory)

        modified:   cpp/ql/src/semmle/code/cpp/models/Models.qll

Untracked files:
  (use "git add <file>..." to include in what will be committed)

        cpp/ql/src/semmle/code/cpp/models/implementations/CustomMemcpy.qll

no changes added to commit (use "git add" and/or "git commit -a")
```